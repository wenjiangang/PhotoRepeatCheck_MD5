package md5calculate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class md5Generator {
	
	/**
	 * ��ȡһ���ļ���md5ֵ(�ɴ�����ļ�)
	 * @return md5 value
	 */

	public static String getMD5(File file) {
		FileInputStream fileInputStream = null;
		try {
			MessageDigest MD5 = MessageDigest.getInstance("MD5");
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
	 * ��һ���ַ�����md5ֵ
	 * @param target �ַ���
	 * @return md5 value
	 */

	public static String MD5(String string) {
		return DigestUtils.md5Hex(string);
	}

	public static void main(String[] args) {
		long beginTime = System.currentTimeMillis();
		File file = new File("C:/Users/jacky/Desktop/a.txt"); //����
		String md5 = getMD5(file);
		File file1 = new File("C:/Users/jacky/Desktop/b.txt");
		String md51 = getMD5(file1);
		long endtime = System.currentTimeMillis();
		System.out.println("MD5:\n" + md5 +"\n"+md51+ "\n ��ʱ:" + ((endtime - beginTime) / 1000) + "s");
	}

}
