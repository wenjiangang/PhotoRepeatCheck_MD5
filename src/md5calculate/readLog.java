package md5calculate;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * ����1-8¥�Ľ��飬�Ż��˴���
 */
public class readLog {

	public static void main(String args[]) {
		String pathname = "E:/input.txt";
		String text = "hello=2.txt";
		writeFile(pathname, text);
		System.out.println(readFile(pathname));
	}

	/**
	 * ����TXT�ļ�
	 */
	public static HashMap<String, String> readFile(String pathname) {
		// ����·�������·�������ԣ�д���ļ�ʱ��ʾ���·��,��ȡ����·����input.txt�ļ�
		// ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw;
		// ���ر��ļ��ᵼ����Դ��й¶����д�ļ���ͬ��
		// Java7��try-with-resources�������Źر��ļ����쳣ʱ�Զ��ر��ļ�����ϸ���https://stackoverflow.com/a/12665271
		HashMap<String, String> list = new HashMap<>();
		try (FileReader reader = new FileReader(pathname); BufferedReader br = new BufferedReader(reader) // ����һ�����������ļ�����ת�ɼ�����ܶ���������
		) {
			String line;
			// �����Ƽ����Ӽ���д��
			String[] aa = null;
			while ((line = br.readLine()) != null) {
				// һ�ζ���һ������
				aa = line.split("=");
				System.out.println("���ڶ�ȡ--->"+line);
				list.put(aa[0], aa[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * д��TXT�ļ�
	 */
	public static void writeFile(String pathname, String text) {

		try {
			File writeName = new File(pathname);
			if (!writeName.exists()) {
				writeName.createNewFile(); // ��������ڵĻ��������ļ�
			}
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeName, true)));
				out.write(text + "\r\n");
				System.out.println("����д��--->"+text);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
