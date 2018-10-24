package md5calculate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

//��ĳ���ļ����ڵ��ļ�����ɾ���ظ��Ĺ��ܣ�������Խ�һ����Ƭ�ļ����е�������һ������ʵ����ͬһ�ŵ��ظ���Ƭɾ��
//ʹ��ʱ���滻"�ļ���Ŀ¼"Ϊ�Լ��ı����ļ���Ŀ¼

public class deleteRepeatPhotos {
	public static void main(String[] args) {
		String filepath = "�ļ���Ŀ¼";
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
						System.out.println("successfully deleted!"); //ɾ���Ѿ����ڵ��ļ�
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
