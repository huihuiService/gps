package com.twinmask.gps.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * StringUtil：字符串工具类，提供字符串公用操作函数
 */
public class StringUtils {

    /**
     * 判断是否为数字
     */
    public static boolean isNum(String str) {
        return str == null || "".equals(str) ? false : str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }

    /**
     * 判断字符串是否为空字符。
     */
    public static boolean isEmpty(String str) {
        return str != null && "".equals(str) ? true : false;
    }

    /**
     * 判断字符串是否为null
     */
    public static boolean isNull(String str) {
        return str == null ? true : false;
    }

    /**
     * 判断字符串是否为空字符串或者null。
     */
    public static boolean isNullOrEmpty(String str) {
        return isNull(str) || isEmpty(str);
    }

    /**
     * NULL转换成空串
     */
    public static String nullToEmpty(Object obj) {
        return obj == null || "null".equals(obj.toString().toLowerCase()) ? "" : obj.toString();
    }

    /**
     * 字符串替换函数。
     *
     * @param key          被替换的值(查找的值)
     * @param replaceValue 替换的值
     * @param value        需要查找替换的字符串
     */
    public static String replace(String str, String key, String replaceValue) {
        if (!isNullOrEmpty(str) && !isNull(key) && !isNull(replaceValue)) {
            int pos = str.indexOf(key);
            if (pos >= 0) {
                int length = str.length();
                int start = pos;
                int end = pos + key.length();
                if (length == key.length()) {
                    str = replaceValue;
                } else if (end == length) {
                    str = str.substring(0, start) + replaceValue;
                } else {
                    str = str.substring(0, start) + replaceValue + replace(str.substring(end), key, replaceValue);
                }
            }
        }
        return str;
    }

    /**
     * 从左起截取字符串前n位
     */
    public static String subOnLeft(String str, int length) {
        if (isNullOrEmpty(str) || length < 0)
            return "";
        if (str.length() < length)
            return str;
        return str.substring(0, length);
    }

    /**
     * 从右起截取字符串后n位
     */
    public static String subOnRight(String str, int length) {
        if (isNullOrEmpty(str) || length < 0 || str.length() < length)
            return "";
        return str.substring(str.length() - length);
    }

    /**
     * 取指定字符串中间n位，字符串的位置从1开始算起。
     *
     * @param str        指定字符串
     * @param beginIndex 开始位置（包含）
     * @param endIndex   结束位置（包含）
     */
    public static String subOnMid(String str, int beginIdex, int endIndex) {
        if (isNull(str))
            return "";
        return str.substring(beginIdex - 1, endIndex);
    }

    /**
     * 去空格
     */
    public static String trimEmpty(String str) {
        return isNull(str) ? "" : str.trim();
    }

    /**
     * 截取字符串后面部分字符,后面加省略号.
     */
    public static String trimWords(String str, int length) {
        return trimWords(str, length, true);
    }

    /**
     * 截取字符串后面部分字符.
     *
     * @param str
     * @param length    表示双字节长度,比如4,表示返回的字符串长度为8字节.
     * @param addPoints
     */
    public static String trimWords(String str, int length, boolean addPoints) {
        String wordStr = str;
        if (isNullOrEmpty(wordStr)) {
            return "";
        }
        int byteLen = length * 2;
        byte[] strBytes = wordStr.getBytes();
        if (strBytes.length == str.length()) {
            if (strBytes.length <= byteLen) {
                return wordStr;
            }
            byte[] trimBytes = new byte[byteLen];
            System.arraycopy(strBytes, 0, trimBytes, 0, byteLen);
            wordStr = new String(trimBytes);
        } else {
            if (wordStr.length() <= length) {
                return str;
            }
            wordStr = StringUtils.subOnLeft(str, length);
        }
        if (str.length() > length && addPoints) {
            wordStr += "...";
        }
        return wordStr;
    }

    /**
     * 字符串split函数的反操作。
     *
     * @param arr      需要反操作的字符串数组
     * @param qutoeStr 数组元素引号字符串
     * @param token    数组元素的分隔字符
     */
    public static String unSplit(String[] arr, String token, String qutoeStr) {
        StringBuffer strbuf = new StringBuffer();
        if (arr == null || arr.length == 0)
            return "";
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                strbuf.append(qutoeStr + arr[i] + qutoeStr);
            } else {
                strbuf.append(qutoeStr + arr[i] + qutoeStr + token);
            }
        }
        return strbuf.toString();
    }

    /**
     * 将 String 转换成 Integer
     */
    public static Integer valueOf(String str) {
        return !isNullOrEmpty(str) ? Integer.valueOf(str) : 0;
    }

    /**
     * 将 Integer 转换成 String
     */
    public static String valueOf(int num) {
        return String.valueOf(num);
    }

    /**
     * 字符串编码函数
     *
     * @param str
     * @param srcCode    字符串原编码
     * @param targetCode 字符串目标编码
     */
    public static String strEncoder(String str, String srcCode, String targetCode) {
        try {
            if (isNull(str))
                return null;
            byte[] bytesStr = str.getBytes(srcCode);
            return new String(bytesStr, targetCode);
        } catch (Exception ex) {
            return str;
        }
    }

    /**
     * 字符串编码函数
     *
     * @param str
     * @param targetCode 字符串目标编码
     */
    public static String strEncoder(String str, String targetCode) {
        try {
            if (isNull(str))
                return null;
            byte[] bytesStr = str.getBytes();
            return new String(bytesStr, targetCode);
        } catch (Exception ex) {
            return str;
        }

    }

    /**
     * 字符串URLDecoder解码 默认UTF-8
     */
    public static String urlDecoder(String str) {
        try {
            str = urlDecoder(str, "UTF-8");
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 字符串URLDecoder解码 默认UTF-8
     */
    public static String urlDecoder(String str, String enc) {
        try {
            if (isNullOrEmpty(enc)) {
                enc = "UTF-8";
            }
            str = URLDecoder.decode(str, enc);
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 字符串URLEncoder编码 默认UTF-8
     */
    public static String urlEncoder(String str) {
        try {
            str = urlEncoder(str, "UTF-8");
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * 字符串URLEncoder编码 默认UTF-8
     */
    public static String urlEncoder(String str, String enc) {
        try {
            if (isNullOrEmpty(enc)) {
                enc = "UTF-8";
            }
            str = URLEncoder.encode(str, enc);
        } catch (Exception e) {
        }
        return str;
    }

    /**
     * UNICODE 转换成 中文
     */
    public static String unicodeDecode(String str) {
        char aChar;
        int len = str.length();
        StringBuffer outBuffer = new StringBuffer(len);
        for (int x = 0; x < len; ) {
            aChar = str.charAt(x++);
            if (aChar == '\\') {
                aChar = str.charAt(x++);
                if (aChar == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        aChar = str.charAt(x++);
                        switch (aChar) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char) value);
                } else {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            } else {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    /**
     * byte数组转换成16进制字符串
     *
     * @param src
     * @return
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    /**
     * byte数组转换成16进制字符串 数组
     *
     * @param src
     * @return
     */
    public static String[] bytesToHexStringByte(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        String[] resultArray = new String[src.length];
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                hv = "0" + hv;
            }
            //System.out.println(Integer.valueOf(hv, 16));
            resultArray[i] = hv.toUpperCase();
        }
        return resultArray;
    }

    /**
     * byte数组转换成16进制int数组
     *
     * @param src
     * @return
     */
    public static int[] bytesToHexIntByte(byte[] src) {
        if (src == null || src.length <= 0) {
            return null;
        }
        int[] resultArray = new int[src.length];
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            resultArray[i] = Integer.valueOf(hv, 16);
        }
        return resultArray;
    }

    public static String[] bytesMessageParseToByte(String[] bytes, int start, int end) {
        String[] strs = new String[end - start + 1];
        int j = 0;
        for (int i = start; i <= end; i++) {
            strs[j] = bytes[i];
            j++;
        }
        return strs;
    }

    public static byte[] bytesMessageParseToByte(byte[] bytes, int start, int end) {
        byte[] strs = new byte[end - start + 1];
        int j = 0;
        for (int i = start; i <= end; i++) {
            strs[j] = bytes[i];
            j++;
        }
        return strs;
    }

    public static String bytesMessageParseToStr(String[] bytes, int start, int end) {
        StringBuffer strs = new StringBuffer();
        for (int i = start; i <= end; i++) {
            strs.append(bytes[i]);
        }
        return strs.toString();
    }

    public static String bytesMessageParseToStr(byte[] bytes, int start, int end) {
        StringBuffer strs = new StringBuffer();
        for (int i = start; i <= end; i++) {
            strs.append(bytes[i]);
        }
        return strs.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }

    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static String[] concat(String[] a, String[] b) {
        String[] c = new String[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    public static String addZeroToFirst(String str, int toLength) {
        while (true) {
            if (str.length() == toLength) {
                return str;
            }
            str = "0" + str;
        }
    }

    public static byte getXor(byte[] datas) {
        byte temp = datas[0];
        for (int i = 1; i < datas.length; i++) {
            temp ^= datas[i];
        }
        return temp;
    }

    private static byte uniteBytes(String src0, String src1) {
        byte b0 = Byte.decode("0x" + src0).byteValue();
        b0 = (byte) (b0 << 4);
        byte b1 = Byte.decode("0x" + src1).byteValue();
        byte ret = (byte) (b0 | b1);
        return ret;
    }

    /**
     * bytes转换成十六进制字符串
     */
    public static byte[] hexStr2Bytes(String src) {
        int m = 0, n = 0;
        int l = src.length() / 2;
        //logger.info(l);
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            m = i * 2 + 1;
            n = m + 1;
            ret[i] = uniteBytes(src.substring(i * 2, m), src.substring(m, n));
        }
        return ret;
    }

    /**
     * 十六进制转二进制
     *
     * @param hexString
     * @return
     */
    public static String hexString2binaryString(String hexString) {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++) {
            tmp = "0000" + Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }

    /**
     * 二进制转十六进制
     *
     * @param bString
     * @return
     */
    public static String binaryString2hexString(String bString) {
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)
            return null;
        StringBuffer tmp = new StringBuffer();
        int iTmp = 0;
        for (int i = 0; i < bString.length(); i += 4) {
            iTmp = 0;
            for (int j = 0; j < 4; j++) {
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
            }
            tmp.append(Integer.toHexString(iTmp));
        }
        return tmp.toString();
    }
}
