package md5calculate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class deleteRepeatPhotos {
	public static void main(String[] args) {
		String filepath = "C:/Users/Unicom/Desktop/new";
		ArrayList<String> list = filesArray.getFiles(filepath);
		System.out.println("files numbers:" + list.size());
		String[] md5lists = new String[list.size()];
		HashMap<String, String> dict=new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			File t = new File(list.get(i).toString());
			System.out.println(list.get(i));
			md5lists[i] = md5Generator.getMD5(t);
			if (dict.get(md5lists[i])!=null){
				try{
					System.out.println(dict.get(md5lists[i])+"repeated!!!!!!!!");
//					if (t.delete()) {
//						System.out.println("deleted!!!!!"); //删除已经存在的文件
//					}
				}catch (Exception e){
					e.printStackTrace();
				}				
			}
			else {
				dict.put(md5lists[i], list.get(i).toString());
			}
		}
		System.out.println(dict+"\r\n");
		System.out.println("NUMBERS:"+dict.size()+"\r\n Finished!\r\n");
		
		
		
	}
}
