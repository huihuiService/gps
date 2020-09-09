package com.twinmask.gps.msgservice.protocol.handler;

import com.twinmask.gps.utils.StringUtils;
import com.twinmask.gps.utils.decode.AES128Utils;
import com.twinmask.gps.utils.decode.RSAUtilsByMoot;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerRead300Handler extends ChannelInboundHandlerAdapter {

    public static Logger logger = LoggerFactory.getLogger(ServerRead300Handler.class);

    public final static String KEY = "qwe4567zxc177456";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object message) throws Exception {
        byte[] msgBytes = (byte[]) message;
        String[] hexArray = StringUtils.bytesToHexStringByte(msgBytes);

        //int length = Integer.valueOf(StringUtils.bytesMessageParseToStr(hexArray, 0, 1), 16);
        String type = StringUtils.bytesMessageParseToStr(hexArray, 0, 1); //类型,长度2
        String version = StringUtils.bytesMessageParseToStr(hexArray, 2, 3); //版本,长度2
        String messageId = StringUtils.bytesMessageParseToStr(hexArray, 4, 5); //消息种类id,长度2
        String clientKey = StringUtils.bytesMessageParseToStr(hexArray, 6, 25); //消息种类id,长度20
        //String messageNo = StringUtils.bytesMessageParseToStr(hexArray, 26, 27); //消息流水,长度2
        if ("0100".equals(messageId)) {
            //String sign = StringUtils.bytesMessageParseToStr(hexArray, 28, 59); //签权码,长度32

            String returnStr = null;
            boolean isSTX = false;
            int newLengthIndex = 60;
            try {
                //杨工协议隐藏下面两行
                //StringUtils.bytesMessageParseToByte(hexArray, newLengthIndex, 61); //数据长度，小军新协议有
                newLengthIndex = newLengthIndex + 2;//消息体加密前的长度

                int endIndex = hexArray.length - 2;
                byte[] content = StringUtils.bytesMessageParseToByte(msgBytes, newLengthIndex, endIndex); //签权码,长度32

                String cStr = new String(content);
                returnStr = RSAUtilsByMoot.decrypt(cStr, cStr.length());
                if (!returnStr.startsWith("{")) {
                    returnStr = "{" + returnStr.substring(24) + "}";
                }
            } catch (Exception ex) {
                isSTX = true;
                int endIndex = hexArray.length - 2;
                byte[] content = StringUtils.bytesMessageParseToByte(msgBytes, newLengthIndex, endIndex); //签权码,长度32

                String cStr = new String(content);
                returnStr = RSAUtilsByMoot.decrypt(cStr, cStr.length());
                returnStr = returnStr.substring(24);
            }
            //String authenticationCode = returnStr.substring(0, 24);
            logger.info(returnStr);

            String resultStr = "";
            if (!isSTX) {
                //String resultStr = type + version + "9100" + clientKey + messageNo + "0003" + "00" + StringUtils.toHexString(KEY);
                //String body = "30303031" + "3030" + StringUtils.toHexString(KEY);
                String body = "0001" + "00" + KEY; //消息体长度++KEY
                body = RSAUtilsByMoot.encryptToHex(body, body.length());
                resultStr = type + version + "9100" + clientKey + "0001" + "0001" + body;
            } else {
                String body = KEY;
                body = RSAUtilsByMoot.encryptToHex(body, body.length());
                resultStr = type + version + "9100" + clientKey + "0001" + "0001" + "00" + body;
            }

            String len = StringUtils.addZeroToFirst(Integer.toHexString(resultStr.length() / 2 + 2 + 1), 4);
            resultStr = len + resultStr;

            byte[] reStrBytes = StringUtils.hexStringToBytes(resultStr);
            ctx.write(StringUtils.concat(reStrBytes, new byte[]{StringUtils.getXor(reStrBytes)}));
        } else if ("0200".equals(messageId)) { //解析数据
            //String body = "$GPRS,862877030744740;R005;!"; //消息体长度++KEY
            //sendCMD(ctx, type, version, clientKey, body);
            byte[] aesBytes = null;
            int newLengthIndex = 28;
            try {
                //杨工协议隐藏下面两行
                //StringUtils.bytesMessageParseToByte(hexArray, newLengthIndex, 61); //数据长度，小军新协议有
                newLengthIndex = newLengthIndex + 2;

                int endIndex = hexArray.length - 2;
                byte[] content = StringUtils.bytesMessageParseToByte(msgBytes, newLengthIndex, endIndex); //数据体
                aesBytes = AES128Utils.decrypt(content, KEY);
            } catch (Exception ex) {
                try {
                    int endIndex = hexArray.length - 2;
                    byte[] content = StringUtils.bytesMessageParseToByte(msgBytes, newLengthIndex, endIndex); //数据体
                    aesBytes = AES128Utils.decrypt(content, KEY);
                } catch (Exception e) {
                    logger.info("AES128 Parse error by parser!");
                    return;
                }
            }
            String returnStr = new String(aesBytes, "UTF-8");

            if (returnStr != null && (returnStr.startsWith("$MG") || returnStr.startsWith("STX"))) {
                //TCPMain.channelMap.put(ctx.channel().id().asLongText(), new MyChannel(ctx.channel(), true, type, version, clientKey));
                //GprmcParser.doParse(ctx, returnStr.getBytes()); //GPRMC数据
                //处理GPRMC数据
            } else {
                logger.info(returnStr);
            }
        } else if ("0104".equals(messageId) || "0105".equals(messageId) || "0106".equals(messageId)) { //解析数据
            byte[] aesBytes = null;
            int newLengthIndex = 28;
            try {
                //杨工协议隐藏下面两行
                //StringUtils.bytesMessageParseToByte(hexArray, newLengthIndex, 61); //数据长度，小军新协议有
                newLengthIndex = newLengthIndex + 2;

                int endIndex = hexArray.length - 2;
                byte[] content = StringUtils.bytesMessageParseToByte(msgBytes, newLengthIndex, endIndex); //数据体
                aesBytes = AES128Utils.decrypt(content, KEY);
            } catch (Exception ex) {
                try {
                    int endIndex = hexArray.length - 2;
                    byte[] content = StringUtils.bytesMessageParseToByte(msgBytes, newLengthIndex, endIndex); //数据体
                    aesBytes = AES128Utils.decrypt(content, KEY);
                } catch (Exception e) {
                    logger.info("AES128 Parse error by parser!");
                    return;
                }
            }

            if (aesBytes != null) {
                //TCPMain.channelMap.put(ctx.channel().id().asLongText(), new MyChannel(ctx.channel(), true, type, version, clientKey));
                //GprmcLBSParser.doParse(ctx, aesBytes); //GPRMC数据
            } else {
                logger.info("0104 is NULL.");
            }
        } else { //解析数据
            String returnStr = null;
            byte[] aesBytes = null;
            int newLengthIndex = 28;
            try {
                newLengthIndex = newLengthIndex + 2;

                int endIndex = hexArray.length - 2;
                byte[] content = StringUtils.bytesMessageParseToByte(msgBytes, newLengthIndex, endIndex); //数据体
                aesBytes = AES128Utils.decrypt(content, KEY);
                returnStr = new String(aesBytes, "UTF-8");
                if (!returnStr.startsWith("{")) {
                    returnStr = "{" + returnStr + "}";
                }
            } catch (Exception ex) {
                try {
                    int endIndex = hexArray.length - 2;
                    byte[] content = StringUtils.bytesMessageParseToByte(msgBytes, newLengthIndex, endIndex); //数据体
                    aesBytes = AES128Utils.decrypt(content, KEY);
                    returnStr = new String(aesBytes, "UTF-8");
                } catch (Exception e) {
                    logger.info("AES128 Parse error by other!");
                    return;
                }
            }
            if (returnStr != null) {
                returnStr = returnStr.replace("{", "").replace("}", "");
                if (returnStr.startsWith("$")) {
                    //CommandCSParser.doParse(ctx, returnStr.substring(0, returnStr.length() - 4).getBytes()); //指令解析
                } else if (returnStr.startsWith("FA")) {
                    //CommandCSParser.doParse(ctx, returnStr.substring(0, returnStr.length()).getBytes());
                } else {
                    logger.info(returnStr);
                }
            }
        }
    }

    public void sendCMD(ChannelHandlerContext ctx, String type, String version, String clientKey, String body) throws Exception {
        byte[] bodyBtye = AES128Utils.encrypt(body, KEY);
        String headStr = type + version + "9101" + clientKey + "0001" + StringUtils.addZeroToFirst(Integer.toHexString(body.length()), 4);

        int headLen = headStr.length();
        String len = StringUtils.addZeroToFirst(Integer.toHexString(2 + headLen / 2 + bodyBtye.length + 1), 4);
        headStr = len + headStr;

        byte[] reStrBytes = StringUtils.hexStringToBytes(headStr);

        reStrBytes = StringUtils.concat(reStrBytes, bodyBtye);

        byte[] reBytes = StringUtils.concat(reStrBytes, new byte[]{StringUtils.getXor(reStrBytes)});

        ctx.write(reBytes);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.ALL_IDLE) {//IdleState.READER_IDLE IdleState.WRITER_IDLE
                System.out.println("IDLE 900S:" + ctx.channel().id().asShortText().toUpperCase());
                ctx.close();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        logger.error("{}", ctx.channel().id().asShortText().toUpperCase(), cause);
        ctx.close();
    }
}
