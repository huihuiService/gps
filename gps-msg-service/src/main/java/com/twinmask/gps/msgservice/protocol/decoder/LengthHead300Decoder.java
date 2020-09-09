package com.twinmask.gps.msgservice.protocol.decoder;

import com.twinmask.gps.utils.StringUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class LengthHead300Decoder extends ByteToMessageDecoder {

    public static Logger logger = LoggerFactory.getLogger(LengthHead300Decoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        try {
            // 防止socket字节流攻击,防止客户端传来的数据过大 ,因为太大的数据是不合理的
            if (buffer.readableBytes() > 1024 * 4) {
                buffer.skipBytes(buffer.readableBytes());
                ctx.close();
                return;
            }

            if (buffer.readableBytes() >= 3) {
                // 有数据时，读取 4 字节判断消息长度
                byte[] sizeBytes = new byte[2];

                //标记当前位置，以便 reset
                buffer.markReaderIndex();

                // 读取数据
                buffer.readBytes(sizeBytes);

                String[] hexArray = StringUtils.bytesToHexStringByte(new byte[]{sizeBytes[0], sizeBytes[1]});
                int length = 0;
                try {
                    length = Integer.valueOf(StringUtils.bytesMessageParseToStr(hexArray, 0, 1), 16) - 2;
                } catch (Exception ex) {
                    buffer.skipBytes(buffer.readableBytes());
                    ctx.close();
                    return;
                }
                if (length > buffer.readableBytes()) {
                    // 还原读指针,等待下一轮回处理
                    buffer.resetReaderIndex(); //0
                    return;
                }
                byte[] dataBytes = new byte[length];
                buffer.readBytes(dataBytes, 0, length);
                out.add(dataBytes);
            }
        } catch (Exception ex) {
            buffer.skipBytes(buffer.readableBytes());
            ctx.close();
            logger.trace("", ex);
        }
    }
}
