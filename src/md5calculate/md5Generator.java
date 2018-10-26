package md5calculate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class md5Generator {
	
	/**
	 * 获取一个文件的md5值(可处理大文件)
	 * @return md5 value
	 */

	public static String getMD5(File file) {
		FileInputStream fileInputStream = null;
		try {
			MessageDigest MD5 = MessageDigest.getInstance("MD5");//采用MD5算法，部分空的文件可能具有相同的md5值，也可以使用SHA1、SHA256
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fileInputStream.read(buffer)) != -1) {
				MD5.update(buffer, 0, length);
			}
			return new String(Hex.encodeHex(MD5.digest()));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	
	/**
	 * 求一个字符串的md5值
	 * @param target 字符串
	 * @return md5 value
	 */

	public static String MD5(String string) {
		return DigestUtils.md5Hex(string);
	}

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		File file = new File("C:/Users/Unicom/Desktop/source/a.txt"); //测试，空的txt和word具有相同的md5
		System.out.println(file);
		
		String md5 = getMD5(file);
		File file1 = new File("C:/Users/Unicom/Desktop/source/b.docx");
		String md5_1 = getMD5(file1);
		long endtime = System.currentTimeMillis();
		System.out.println("MD5:\n" + md5 +"\n"+md5_1+ "\n 耗时:" + ((endtime - beginTime) ) + "ms");
	}

}
