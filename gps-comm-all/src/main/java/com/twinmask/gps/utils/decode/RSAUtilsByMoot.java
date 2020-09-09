package com.twinmask.gps.utils.decode;

public class RSAUtilsByMoot {

	final static int RSA_N = 15;
	final static int RSA_E = 3;
	final static int RSA_D = 11;

	public static String encrypt(String plaintext, int len) {
		String cipher = "";
		char[] tmp = new char[4];
		int powresult = 0;
		int i = 0;
		int j = 0;
		for (i = 0; i < len; i++) {
			tmp[0] = (char) (plaintext.charAt(i) & 0x03);
			tmp[1] = (char) ((plaintext.charAt(i) >> 2) & 0x03);
			tmp[2] = (char) ((plaintext.charAt(i) >> 4) & 0x03);
			tmp[3] = (char) ((plaintext.charAt(i) >> 6) & 0x03);
			for (j = 0; j < 4; j++) {
				powresult = rsa_pow(tmp[j], RSA_E);
				cipher += (char) (powresult % (int) RSA_N);
			}
		}
		return cipher;
	}

	public static String encryptToHex(String plaintext, int len) {
		String cipher = "";
		char[] tmp = new char[4];
		int powresult = 0;
		int i = 0;
		int j = 0;
		for (i = 0; i < len; i++) {
			tmp[0] = (char) (plaintext.charAt(i) & 0x03);
			tmp[1] = (char) ((plaintext.charAt(i) >> 2) & 0x03);
			tmp[2] = (char) ((plaintext.charAt(i) >> 4) & 0x03);
			tmp[3] = (char) ((plaintext.charAt(i) >> 6) & 0x03);
			for (j = 0; j < 4; j++) {
				powresult = rsa_pow(tmp[j], RSA_E);
				String str = Integer.toHexString((powresult % (int) RSA_N));
				if (str.length() < 2) {
					str = "0" + str;
				}
				cipher += str;
			}
		}
		return cipher;
	}

	public static String decrypt(String cipher, int len) {
		StringBuffer plaintext = new StringBuffer();
		int d1 = RSA_D / 2;
		int d2 = RSA_D - d1;

		char[] tmp = new char[4];

		int cd1 = 0, cd2 = 0;
		int swap = 0, modtmp = 0;
		int i = 0, j = 0;
		for (i = 0; i < len; i += 4) {
			for (j = 0; j < 4; j++) {
				//System.out.println(i + j);
				char cStr = cipher.charAt(i + j);
				if (cStr == 0 || cStr == 1) {
					tmp[j] = cStr;
				} else {
					cd1 = (int) (rsa_pow(cStr, d1) % RSA_N);
					cd2 = (int) (rsa_pow(cStr, d2) % RSA_N);

					if (cd1 < cd2) {
						swap = cd1;
						cd1 = cd2;
						cd2 = swap;
					}
					modtmp = (int) (cd1 % cd2);
					tmp[j] = (char) (modtmp > 0 ? modtmp : cd1 / cd2);
				}
			}
			int c = (tmp[0]) | (tmp[1] << 2) | (tmp[2] << 4) | (tmp[3] << 6);
			plaintext.append((char) c);
		}
		return plaintext.toString();
	}

	public static String decrypt(byte[] cipher, int len) {
		StringBuffer plaintext = new StringBuffer();
		int d1 = RSA_D / 2;
		int d2 = RSA_D - d1;

		char[] tmp = new char[4];

		int cd1 = 0, cd2 = 0;
		int swap = 0, modtmp = 0;
		int i = 0, j = 0;
		for (i = 0; i < len; i += 4) {
			for (j = 0; j < 4; j++) {
				char cStr = (char) cipher[i + j];
				if (cStr == 0 || cStr == 1) {
					tmp[j] = cStr;
				} else {
					cd1 = (int) (rsa_pow(cStr, d1) % RSA_N);
					cd2 = (int) (rsa_pow(cStr, d2) % RSA_N);

					if (cd1 < cd2) {
						swap = cd1;
						cd1 = cd2;
						cd2 = swap;
					}
					modtmp = (int) (cd1 % cd2);
					tmp[j] = (char) (modtmp > 0 ? modtmp : cd1 / cd2);
				}
			}
			int c = (tmp[0]) | (tmp[1] << 2) | (tmp[2] << 4) | (tmp[3] << 6);
			plaintext.append((char) c);
		}
		return plaintext.toString();
	}

	static int rsa_pow(char x, int y) {
		int result = 1;
		while (y > 0) {
			result *= x;
			y--;
		}
		return result;
	}

	public static void main(String[] args) {
		String text = "000100TWINMASKWIFILBS2";
		int textLen = text.length();
		String rsaCipher = RSAUtilsByMoot.encrypt(text, textLen);

		String rsaPlaintext = RSAUtilsByMoot.decrypt(rsaCipher, textLen * 4);

		System.out.println("Encode:" + rsaCipher);
		System.out.println("Decode:" + rsaPlaintext);
	}
}
