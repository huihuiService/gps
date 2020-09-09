package com.twinmask.gps.msgservice.protocol.handler;

import com.twinmask.gps.msgservice.protocol.parser.ParserSupport;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ServerReadHandler extends ChannelInboundHandlerAdapter {

    static final Logger logger = LoggerFactory.getLogger(ServerReadHandler.class);

    private ParserSupport parser;

    public ServerReadHandler(ParserSupport parser){
        this.parser = parser;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) {
        try {
            byte[] msgBytes = (byte[]) message;
            if (msgBytes.length == 0) {
                return;
            }
            parser.doParse(ctx, msgBytes);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {
                logger.trace("IDLE 900S:{}", ctx.channel().id().asShortText().toUpperCase());
                ctx.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("", cause);
        ctx.close();
    }
}
