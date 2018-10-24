package md5calculate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

//对某个文件夹内的文件进行删除重复的功能，比如可以将一个照片文件夹中的命名不一样但是实际是同一张的重复照片删除
//使用时请替换"文件夹目录"为自己的本地文件夹目录

public class deleteRepeatPhotos {
	public static void main(String[] args) {
		String filepath = "文件夹目录";
		ArrayList<String> list = filesArray.getFiles(filepath);
		System.out.println("files numbers:" + list.size());
		String[] md5lists = new String[list.size()];
		HashMap<String, String> dict=new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			File t = new File(list.get(i).toString());
			md5lists[i] = md5Generator.getMD5(t);
			if (dict.get(md5lists[i])!=null){
				try{
					System.out.println(dict.get(md5lists[i])+"  :  repeat!");
					if (t.delete()) {
						System.out.println("successfully deleted!"); //删除已经存在的文件
					}
				}catch (Exception e){
					e.printStackTrace();
				}				
			}
			else {
				dict.put(md5lists[i], list.get(i).toString());
			}
		}
		System.out.println("lefted pictures:"+dict.size()+"\r\n successfully finished!\r\n");
		
		
		
	}
}
