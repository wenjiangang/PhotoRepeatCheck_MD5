package md5calculate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * 对某个文件夹内的文件进行删除重复的功能，比如可以将一个照片文件夹中的命名不一样但是实际是同一张的重复照片删除
 * 使用时请替换"文件夹目录"为自己的本地文件夹目录
 * 
 */


public class deleteRepeat {
	public static void deleteRepeat(String path){
		ArrayList<String> list = filesArray.getFiles(path);
		System.out.println("files numbers:" + list.size());
		String[] md5lists = new String[list.size()];
		HashMap<String, String> dict=new HashMap<String, String>();
		ArrayList<String> deletedlist=new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			File t = new File(list.get(i).toString());
			md5lists[i] = md5Generator.getMD5(t);
			if (dict.get(md5lists[i])!=null){
				try{
					System.out.println(list.get(i).toString()+"  :  repeat!--->>"+dict.get(md5lists[i]));//如果文件md5只在字典中存在，则提示重复
					if (t.delete()) {
						System.out.println("successfully deleted!"); //删除该重复的文件
					}
					deletedlist.add(md5lists[i]);
				}catch (Exception e){
					e.printStackTrace();
				}				
			}
			else {
				dict.put(md5lists[i], list.get(i).toString()); //如果字典中没有该文件的md5，则将md5存入字典作为key，值为文件路径
			}
		}
		System.out.println("lefted files:"+dict.size()+"\r\n"+deletedlist.size()+" deleted!"+"\r\nsuccessfully finished!\r\n");
		
		
		
	}
	
	public static void main(String[] args) {
		String filepath = "C:/Users/Unicom/Desktop/source";
		deleteRepeat(filepath);
		
	}
}
