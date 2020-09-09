package com.twinmask.gps.msgservice.protocol.decoder;

import com.twinmask.gps.utils.CommUtils;
import com.twinmask.gps.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LengthHeadDecoder extends ByteToMessageDecoder {

    public static Logger logger = LoggerFactory.getLogger(LengthHeadDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        try {
            // 防止socket字节流攻击,防止客户端传来的数据过大 ,因为太大的数据是不合理的
            if (buffer.readableBytes() > 1024 * 4) {
                buffer.skipBytes(buffer.readableBytes());
                ctx.close();
                return;
            }

            if (buffer.readableBytes() >= 4) {
                // 有数据时，读取 4 字节判断消息长度
                byte[] sizeBytes = new byte[4];

                //标记当前位置，以便 reset
                buffer.markReaderIndex();

                // 读取钱 4 个字节
                buffer.readBytes(sizeBytes);

                String number = CommUtils.bytesToString(sizeBytes);
                if (!StringUtils.isNum(number)) {
                    logger.trace("LengthHeadDecoder {}", ctx.channel().id().asShortText().toUpperCase() + " head \"" + number + "\" is not a number!");
                    buffer.skipBytes(buffer.readableBytes());
                    ctx.close();
                    return;
                }

                int length = Integer.valueOf(number);
                if (length > buffer.readableBytes()) {
                    // 还原读指针,等待下一轮回处理
                    buffer.resetReaderIndex();
                } else {
                    byte[] dataBytes = new byte[length];
                    buffer.readBytes(dataBytes, 0, length);
                    out.add(dataBytes);
                }
            }
        } catch (Exception ex) {
            buffer.skipBytes(buffer.readableBytes());
            ctx.close();
            logger.trace("", ex);
        }
    }
}
