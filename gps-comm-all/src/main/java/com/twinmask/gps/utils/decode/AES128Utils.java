package com.twinmask.gps.utils.decode;

import com.twinmask.gps.utils.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES128Utils {

    public static byte[] encrypt(String content, String password) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] byteContent = content.getBytes("utf-8");
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] decrypt(byte[] content, String password) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//1.AES/ECB/PKCS5Padding  2.AES/ECB/NoPadding
        SecretKeySpec secretKey = new SecretKeySpec(password.getBytes(), "AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(content);
    }

    public static void main(String[] args) throws Exception {
        //byte s = (byte)0xA0;
        String password = "TWINMASKWIFILBS2";
        String str = "  2D 30 31 2D 32 39 20 31 33 3A 34 30 3A 31 38 20 7C 20 67 73 65 6E 73 6F 72 20 69 6E 74 32 0D 0A 32 30 31 39 2D 30 31 2D 32 39 20 31 33 3A 34 30 3A 31 38 20 7C 20 61 6E 79 2D 6D 6F 74 69 6F 6E";
        //String str = "B4 5F B2 80 C2 45 EE E2 90 04 A0 B8 7C 47 96 9F CA 27 2D 36 F2 96 52 5E E9 34 C4 50 43 B5 81 87 B6 1F 10 D5 C5 E3 37 56 10 5A 0E 59 DB 3D 7E 1B E7 44 94 4C DB B1 25 1E 5D F3 85 35 85 E9 B3 E6 DC 22 B6 FB 11 7B A0 C1 B9 DD 88 43 FD D0 F5 E9 5D DF 36 8E 8E 81 72 54 D2 D5 CB 6E 5E 73 6F 39 97 98 8F BD 65 15 93 86 A6 04 9D 1A 30 A6 CE E1 3A C9 3C 42 77 FE A3 B7 1C 86 B0 3B AE 15 83 0D 4E 18 A1 2F 14 F7 AB C3 E4 09 8A 72 08 D5 65 F6 5F 7E AC BE FA E0 1D B6 59 7E B0 17 AC B6 7B A2 5B 6E 88 47 58 CC A7 9A 37 1C EC F1 62 B2 10 85 61 FF 82 87 82 D5 71 C0 F1 43 C2 27 6B 23 00 AE D2 48 1B 0C 72 C1 57 26 D3 F2 92 16 4B 40 F5 D9";
        ///byte[] rsaCipher = { 83, -57, 81, 5, -66, -63, -36, -88, -53, 119, -50, -79, 37, -102, 41, 117, 120, -91, -95, 63, 8, -99, 107, -80, 30, -5, 49, 86, 122, -87, -7, -70, 109, -30, -38, -83, -17, 51, 18, 58, -87, -1, 38, 118, 20, 37, -68, 48, 90, -69, -65, 97, 29, -16, 37, -3, -116, 25, 9, 75, -33, -58, -85, 36, 106, 110, 28, 49, -6, 69, -36, 104, -106, 68, -126, 23, 58, -25, -125, 107, 73, -91, 28, 96, -83, 83, -3, 63, -36, -40, 9, -119, 66, 57, 42, -124, 108, 100, -62, -114, 40, -10, 77, 53, 16, 92, 61, 25, 4, 4, 4, 4 };
        //byte[] rsaCipher = AES128Utils.encrypt("$MGV002,,,R,101018,091625,V,2238.29385,N,11401.99096,E,00,00,00,,,,,,,,,,,0000,0000,0,,,,,,11,060,BeltOn,,;!", password);
        str = str.replaceAll(" ", "");
        byte[] rsaCipher = StringUtils.hexStringToBytes(str);
        //System.out.println(rsaCipher.length);
        //byte[] rsaCipher = AES128Utils.encrypt("123456789123456", password);//115, 108, 23, -33, 100, -45, 116, -1, 91, 65, 78, -100, -54, 121, 15, -22
        byte[] rsaPlaintext = AES128Utils.decrypt(rsaCipher, password);

        //System.out.println("Encode:" + rsaCipher);
        str = new String(rsaPlaintext, "UTF-8");
        System.out.println(str.startsWith("p"));
        System.out.println("Decode:" + str);
    }
}
