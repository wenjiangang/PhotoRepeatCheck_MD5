package md5calculate;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 根据1-8楼的建议，优化了代码
 */
public class readLog {

	public static void main(String args[]) {
		String pathname = "E:/input.txt";
		String text = "hello=2.txt";
		writeFile(pathname, text);
		System.out.println(readFile(pathname));
	}

	/**
	 * 读入TXT文件
	 */
	public static HashMap<String, String> readFile(String pathname) {
		// 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
		// 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
		// 不关闭文件会导致资源的泄露，读写文件都同理
		// Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
		HashMap<String, String> list = new HashMap<>();
		try (FileReader reader = new FileReader(pathname); BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
		) {
			String line;
			// 网友推荐更加简洁的写法
			String[] aa = null;
			while ((line = br.readLine()) != null) {
				// 一次读入一行数据
				aa = line.split("=");
				System.out.println("正在读取--->"+line);
				list.put(aa[0], aa[1]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 写入TXT文件
	 */
	public static void writeFile(String pathname, String text) {

		try {
			File writeName = new File(pathname);
			if (!writeName.exists()) {
				writeName.createNewFile(); // 如果不存在的话创建新文件
			}
			BufferedWriter out = null;
			try {
				out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writeName, true)));
				out.write(text + "\r\n");
				System.out.println("正在写入--->"+text);
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
