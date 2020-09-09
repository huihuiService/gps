package com.twinmask.gps.msgservice.protocol.handler;

import com.twinmask.gps.msgservice.protocol.SessionManage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Leo
 */
public class SessionHandler extends ChannelHandlerAdapter {

    static final Logger logger = LoggerFactory.getLogger(SessionHandler.class);

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        logger.trace("SessionHandler Conn :" + ctx.channel().id().asShortText().toUpperCase());
        //SessionGloab.CHANNEL_MAP.put(ctx.channel().id().asLongText(), ctx.channel());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        logger.trace("SessionHandler Close:" + ctx.channel().id().asShortText().toUpperCase());
        SessionManage.CHANNEL_MAP.remove(ctx.channel().id().asLongText());
    }
}
