package md5calculate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * ��ĳ���ļ����ڵ��ļ�����ɾ���ظ��Ĺ��ܣ�������Խ�һ����Ƭ�ļ����е�������һ������ʵ����ͬһ�ŵ��ظ���Ƭɾ��
 * ʹ��ʱ���滻"�ļ���Ŀ¼"Ϊ�Լ��ı����ļ���Ŀ¼
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
					System.out.println(list.get(i).toString()+"  :  repeat!--->>"+dict.get(md5lists[i]));//����ļ�md5ֻ���ֵ��д��ڣ�����ʾ�ظ�
					if (t.delete()) {
						System.out.println("successfully deleted!"); //ɾ�����ظ����ļ�
					}
					deletedlist.add(md5lists[i]);
				}catch (Exception e){
					e.printStackTrace();
				}				
			}
			else {
				dict.put(md5lists[i], list.get(i).toString()); //����ֵ���û�и��ļ���md5����md5�����ֵ���Ϊkey��ֵΪ�ļ�·��
			}
		}
		System.out.println("lefted files:"+dict.size()+"\r\n"+deletedlist.size()+" deleted!"+"\r\nsuccessfully finished!\r\n");
		
		
		
	}
	
	public static void main(String[] args) {
		String filepath = "C:/Users/Unicom/Desktop/source";
		deleteRepeat(filepath);
		
	}
}
