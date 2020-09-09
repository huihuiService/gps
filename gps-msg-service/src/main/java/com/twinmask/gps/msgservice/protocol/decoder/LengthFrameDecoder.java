package com.twinmask.gps.msgservice.protocol.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class LengthFrameDecoder extends LengthFieldBasedFrameDecoder {

	//判断传送客户端传送过来的数据是否按照协议传输
	private static final int HEADER_SIZE = 4;

	private int length;

	/**
	 *
	 * @param maxFrameLength 解码时，处理每个帧数据的最大长度
	 * @param lengthFieldOffset 该帧数据中，存放该帧数据的长度的数据的起始位置
	 * @param lengthFieldLength 记录该帧数据长度的字段本身的长度
	 * @param lengthAdjustment 修改帧数据长度字段中定义的值，可以为负数
	 * @param initialBytesToStrip 解析的时候需要跳过的字节数
	 * @param failFast 为true，当frame长度超过maxFrameLength时立即报TooLongFrameException异常，为false，读取完整个帧再报异常
	 */
	public LengthFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip, boolean failFast) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip, failFast);
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
		if (in == null) {
			return null;
		}
		if (in.readableBytes() < HEADER_SIZE) {
			throw new Exception("可读信息段比头部信息都小，你在逗我？");
		}

		//@Override
		//protected void encode(ChannelHandlerContext ctx, MyProtocolBean msg, ByteBuf out) throws Exception {
		//    if(msg == null){
		//        throw new Exception("msg is null");
		//    }
		//    out.writeByte(msg.getType());
		//    out.writeByte(msg.getFlag());
		//    out.writeInt(msg.getLength());
		//    out.writeBytes(msg.getContent().getBytes(Charset.forName("UTF-8")));
		//}
		//注意在读的过程中，readIndex的指针也在移动 ,对应上面的写入方式
		length = in.readInt();

		if (in.readableBytes() < length) {
			throw new Exception("body字段你告诉我长度是" + length + ",但是真实情况是没有这么多，你又逗我？");
		}
		ByteBuf buf = in.readBytes(length);
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);

		//CustomMsg customMsg = new CustomMsg(type, flag, length, body);
		return req;
	}
}
