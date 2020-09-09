package com.twinmask.gps.msgservice.protocol;

import com.twinmask.gps.msgservice.protocol.decoder.LengthHeadDecoder;
import com.twinmask.gps.msgservice.protocol.handler.ServerReadHandler;
import com.twinmask.gps.msgservice.protocol.handler.ServerWriteHandler;
import com.twinmask.gps.msgservice.protocol.handler.SessionHandler;
import com.twinmask.gps.msgservice.protocol.parser.ServerParserHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Leo
 */
@Component
public class ServerMain {

    static final Logger logger = LoggerFactory.getLogger(ServerMain.class);

    protected final int readerIdleTimeSeconds = 10, writerIdleTimeSeconds = 5, allIdleTimeSeconds = 900;

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    ChannelFuture bindFuture;

    @Autowired
    private ServerParserHandler serverParserHandler;

    public boolean start(int port, boolean headHaveLength) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(childHandlerInitializer(headHaveLength));
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        bootstrap.option(ChannelOption.SO_REUSEADDR, true);
        bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, false);

        bindFuture = bootstrap.bind(port);

        try {
            bindFuture.sync();
            logger.info("ServerMain start finished: {}", bindFuture.isSuccess());
        } catch (InterruptedException e) {
            logger.warn("ServerMain sync interrupt", e);
        }
        return bindFuture.isSuccess();
    }

    public ChannelInitializer<Channel> childHandlerInitializer(boolean headHaveLength) {
        return new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                initChildHandlers(ch, headHaveLength);
            }
        };
    }

    public void initChildHandlers(Channel channel, boolean headHaveLength) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.addLast("channel", new SessionHandler());
        pipeline.addLast("idlehandler", new IdleStateHandler(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds));

        if (headHaveLength) {
            pipeline.addLast("decoder", new LengthHeadDecoder());
        } else {
            //下面这两句就是配置以“$_”为分隔符的解码器
            ByteBuf delimiter1 = Unpooled.copiedBuffer("!".getBytes());
            ByteBuf delimiter2 = Unpooled.copiedBuffer("\r\n".getBytes());
            ByteBuf delimiter3 = Unpooled.copiedBuffer("\n".getBytes());

            //1024 是单条消息的最大长度，如果达到该长度后仍然没有找到分隔符就会抛出异常，这点大家要特别注意。delimiter就是我们的分隔符。
            pipeline.addLast("lineData", new DelimiterBasedFrameDecoder(1024, delimiter1, delimiter2, delimiter3));

            pipeline.addLast("decoder", new ByteArrayDecoder());
        }
        pipeline.addLast("encoder", new ByteArrayEncoder());

        pipeline.addLast(new ServerWriteHandler());
        ServerReadHandler readHandler = new ServerReadHandler(serverParserHandler);
        pipeline.addLast(readHandler);
    }

    public boolean stop() {
        if (bindFuture == null || !bindFuture.isSuccess()) {
            return false;
        }
        ChannelFuture closeFuture = bindFuture.channel().close();
        try {
            closeFuture.sync();
            logger.info("ServerMain stop finished: {}", closeFuture.isSuccess());
        } catch (InterruptedException e) {
            logger.warn("ServerMain sync interrupt", e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
        return closeFuture.isSuccess();
    }
}
