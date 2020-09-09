package com.twinmask.gps.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class CommUtils {

	/**
	 * 获取唯一编号
	 */
	public static String getOnlyCode() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date());
	}

	/**
	 * 获得MAC地址
	 */
	public static String getLocalMac() {
		String result = null;
		try {
			InetAddress ia = InetAddress.getLocalHost();
			byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			StringBuffer sb = new StringBuffer("");
			for (int i = 0; i < mac.length; i++) {
				if (i != 0) {
					sb.append("-");
				}
				int temp = mac[i] & 0xff;
				String str = Integer.toHexString(temp);
				if (str.length() == 1) {
					sb.append("0" + str);
				} else {
					sb.append(str);
				}
			}
			result = sb.toString().toUpperCase();
		} catch (Exception ex) {
			result = null;
		}
		return result;
	}

	/**
	 * 生成下一个顺序号。
	 *
	 * @param currSerialNo
	 *            - 数据库中当前最大的顺序号
	 * @param length
	 *            - 顺序号的位数
	 * @return String - 下一个顺序号
	 */
	public static String genNextSerialNo(String currSerialNo, int length) {
		String result = "";

		// 如果currSerialNo不够位数，在前面加“0”。
		if (currSerialNo.length() < length) {
			String zeroStr = "";
			for (int i = length - currSerialNo.length(); i > 0; i--)
				zeroStr += "0";
			currSerialNo = zeroStr + currSerialNo;
		}

		// 产生当前最大顺序号。
		long currMaxSerialNo = Long.parseLong("1" + currSerialNo) + 1;

		// 去掉前面一位数字。
		result = String.valueOf(currMaxSerialNo).substring(1);
		return result;
	}

	/**
	 * byte数组转字符串
	 * @param bytes
	 * @return
	 */
	public static String bytesToString(byte[] bytes) {
		try {
			return new String(bytes, "UTF-8");
		} catch (Exception ex) {
			return null;
		}
	}

	public static int bytesToInt(byte[] bytes) {
		int number = 0;
		number += (bytes[0] & 0xFF);
		number += (bytes[1] & 0xFF) << 8;
		number += (bytes[2] & 0xFF) << 16;
		number += (bytes[3] & 0xFF) << 24;
		return number;
	}

	public static byte[] intToBytes(int number) {
		byte[] bytes = new byte[4];
		bytes[0] = (byte) (number & 0xFF);
		bytes[1] = (byte) ((number >> 8) & 0xFF);
		bytes[2] = (byte) ((number >> 16) & 0xFF);
		bytes[3] = (byte) ((number >> 24) & 0xFF);
		return bytes;
	}

	/**
	 * byte数组转字符串
	 * @param bytes
	 * @param charset 编码
	 * @return
	 */
	public static String bytesToString(byte[] bytes, String charset) {
		try {
			return new String(bytes, charset);
		} catch (Exception ex) {
			return null;
		}
	}

	public static byte[] ReadStrByIndex(byte[] str, int index, int len, boolean flag) {
		byte[] msg = new byte[len];
		int i = 0;
		int sIndex = 0;
		boolean isReading = false;
		try {
			for (byte b : str) {
				if (sIndex == index) {
					break;
				}
				if (b == 44 && !isReading) {
					sIndex++;
					continue;
				}
				if (sIndex + 1 == index) {
					if (flag) {
						isReading = true;
					}
					msg[i] = b;
					i++;
					if (i == len) {
						break;
					}
				}
			}
			if (!flag || i == len) {
				return msg;
			} else {
				return null;
			}
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 图片转字节
	 * @param filePath
	 * @return
	 */
	public static byte[] ReadImageData(String filePath) {
		File f = new File(filePath);
		BufferedImage bi;
		try {
			bi = ImageIO.read(f);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bi, "JPEG", baos);
			return baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	* 获得指定文件的byte数组
	*/
	public static byte[] getBytes(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	/**
	 * 获得字符串中第一次出现的一串数字
	 * @return
	 */
	public static String GetNumberByString(String str) {
		String numberStr = "";
		char[] strs = str.toCharArray();
		for (int i = 0; i < strs.length; i++) {
			if (StringUtils.isNum(strs[i] + "")) {
				numberStr += strs[i];
				if ((i + 1) == strs.length || !StringUtils.isNum(strs[i + 1] + "")) {
					break;
				}
			}
		}
		return numberStr == "" ? "0" : numberStr;
	}

	/**
	 * 读取分隔符前面的字符串，读取完后把从原字符串中删掉
	 * @return
	 */
	public static String ReadStrByDecollator(StringBuffer str, String decollator) {
		if (StringUtils.isNullOrEmpty(str.toString()) || str.indexOf(decollator) == -1) {
			return "";
		}

		int decollatorIndex = str.indexOf(decollator);

		String msg = str.substring(0, decollatorIndex);

		String strs = str.substring(decollatorIndex + decollator.length(), str.length());

		str.replace(0, str.length(), strs);
		return msg;
	}

	/**
	  * 读取文件
	  */
	public static String readFile(File file) throws Exception {
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
		BufferedReader br = new BufferedReader(read);
		StringBuffer sbf = new StringBuffer("");
		String line = null;
		while ((line = br.readLine()) != null) {
			sbf.append(line).append("\r\n");// 按行读取，追加换行\r\n
		}
		br.close();
		return sbf.toString();
	}

	/**
	 * 写入文件
	 */
	public static void writeFile(String savePath, String conent) throws Exception {
		FileWriter writer = null;
		try {
			createFile(savePath);
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(savePath, true);
			writer.write(conent);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
			}
		}
	}

	public static void main(String[] args) {
		//System.err.println("123123!nsdfsadfas!sdaasd".split("!")[2]);
		System.err.println("$MGV002,012896008530237,860719020196568,R,120514,125943,A,2231.9293,N,11356.9932,E,00,10,00,0.97,0.000,183.23,2.1,0.0,460,07,247C,0E30,24,0000,0000,0,2881,2333,26.63,90.19,,,97,Return to normal speed;".length());
		//String pictrueId = "010213040652";
		//System.out.println(20 + pictrueId.substring(4, 6) + pictrueId.substring(2, 4) + pictrueId.substring(0, 2) + pictrueId.substring(6));
		//		try {
		//			byte[] picData = new byte[] { 83, 83 , 83 , 83 , 83 , 83 , 83 };
		//			byte[] newImg = picData;
		//			byte[] oldImg = CommUtil.getBytes("c:/1.jpg");
		//			if (oldImg != null && oldImg.length > 0) {
		//				newImg = new byte[oldImg.length + picData.length];
		//				System.arraycopy(oldImg, 0, newImg, 0, oldImg.length);
		//				System.arraycopy(picData, 0, newImg, oldImg.length, picData.length);
		//			}
		//			writeFile("c:/2.jpg", newImg);
		//		} catch (Exception e) {
		//			e.printStackTrace();
		//		}
	}

	/**
	 * 写入文件
	 */
	public static void writeFile(String filePath, byte[] bfile) throws Exception {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			file = new File(filePath);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.flush();
					bos.close();
				} catch (IOException e1) {
				}
			}
			if (fos != null) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static void createFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			String dir = filePath.substring(0, filePath.lastIndexOf("/"));
			File fileDir = new File(dir);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			file.createNewFile();
		}
	}

	/**
	 * 根据byte数组，生成文件
	 */
	public static void getFile(byte[] bfile, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
				dir.mkdirs();
			}
			file = new File(filePath + "\\" + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(bfile);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 检测程序。
	 *
	 * @param processName 线程的名字，请使用准确的名字
	 * @return 找到返回true,没找到返回false
	 */
	public static boolean findProcesss(String processName) {
		boolean flag = false;
		try {
			Process process = Runtime.getRuntime().exec("cmd /c tasklist");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream os = process.getInputStream();
			byte b[] = new byte[256];
			while (os.read(b) > 0) {
				baos.write(b);
				String s = baos.toString();
				if (s.indexOf(processName) >= 0) {
					flag = true;
				} else {
					flag = false;
				}
			}
		} catch (IOException e) {
		}
		return flag;
	}

	public static void closeProcess(String programName) {
		try {
			Process process = Runtime.getRuntime().exec("cmd /c tasklist");
			InputStream is = process.getInputStream();
			BufferedReader r = new BufferedReader(new InputStreamReader(is));
			String str = null;
			while ((str = r.readLine()) != null) {
				String id = null;
				Matcher matcher = Pattern.compile(programName + "[ ]*([0-9]*)").matcher(str);
				while (matcher.find()) {
					if (matcher.groupCount() >= 1) {
						id = matcher.group(1);
						if (id != null) {
							Integer pid = null;
							try {
								pid = Integer.parseInt(id);
							} catch (NumberFormatException e) {
								e.printStackTrace();
							}
							if (pid != null) {
								Runtime.getRuntime().exec("cmd.exe /c taskkill /f /pid " + pid);
								//System.out.println("kill progress" + pid);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
