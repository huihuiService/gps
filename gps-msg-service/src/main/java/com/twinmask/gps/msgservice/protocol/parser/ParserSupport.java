package com.twinmask.gps.msgservice.protocol.parser;

import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

/**
 * @author Leo
 */
@Service
public interface ParserSupport {

    /**
     * 处理数据
     */
    void doParse(final ChannelHandlerContext ctx, final byte[] msgBytes);
}
