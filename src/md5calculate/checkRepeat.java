package md5calculate;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;

public class checkRepeat {

	public static void main(String[] args) {
	
		String targetdrictory = "C:/Users/jacky/Desktop/冲洗照片";
		String newdrictory = "C:/Users/jacky/Desktop/new";
		String logdrictory = "C:/Users/jacky/Desktop/log.txt";
//		System.out.println(filesArray.getFiles(s));
		ArrayList<String> list = filesArray.getFiles(targetdrictory);
		System.out.println("files numbers:" + list.size());
		long startTime = System.currentTimeMillis();
		String[] md5lists = new String[list.size()];
		Map<String, String> dict = new HashMap<String, String>();



		for (int i = 0; i < list.size(); i++) {

//			System.out.printf(list.get(i) + "---->");
			File t = new File(list.get(i).toString());
//			System.out.println(MD5_test.getMD5(t));

			md5lists[i] = md5Generator.getMD5(t);
			dict.put(md5lists[i], list.get(i).toString());

//			readLog.writeFile(logdrictory, md5lists[i]+"="+list.get(i).toString());
//			System.out.println(i+"--->:"+md5lists[i]);

		}


		System.out.println(dict);
		long endTime = System.currentTimeMillis();
		System.out.println((endTime - startTime) + "ms");

		ArrayList<String> newlist = filesArray.getFiles(newdrictory);
		System.out.println("new files numbers:" + newlist.size());
		Map<String, String> existmd5=new HashMap<String,String>();
		existmd5=readLog.readFile(logdrictory);
		System.out.println(existmd5);//打印出log文档中之前已经存在的md5
		for (int j = 0; j < newlist.size(); j++) {
			File newt = new File(newlist.get(j).toString());
			String newmd5 = md5Generator.getMD5(newt);

			try {
				if (existmd5.get(newmd5) != null) {
					System.out.println(newlist.get(j).toString() + "---exist!!!!--->>" + dict.get(newmd5));
					if (newt.delete()) {
						System.out.println("deleted!!!!!"); //删除在log中已经存在的md5照片，只保留下新的照片
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

		}

	}

}
