package md5calculate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * merge�������ںϲ������ļ��У�pathSourceΪԴĿ¼��pathDestinationΪ�ϲ����Ŀ��Ŀ¼
 * line 25��ɾ��Ŀ��Ŀ¼���ظ����ļ�����ѡ
 * line 36��ɾ��ԴĿ¼���ظ����ļ�����ѡ
 * 
 */

public class mergeDirectory {
	public static void merge(String pathSource,String pathDestination){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
//        System.out.println(df.format(new Date()));// new Date()Ϊ��ȡ��ǰϵͳʱ�䣬ע��import java.util.Date;
		
//		deleteRepeat.deleteRepeat(pathDestination);//��ɾ��Ŀ��Ŀ¼���ظ����ļ�
		ArrayList<String> listDestination = filesArray.getFiles(pathDestination);//���ڴ��Ŀ¼�����е��ļ�·��
		System.out.println("destination file numbers:" + listDestination.size());
		HashMap<String, String> dict=new HashMap<String, String>();//����һ��map���ڴ��Ŀ��·���µ�md5��Ͷ�Ӧ���ļ�·��
		HashMap<String, String> existfilesname=new HashMap<String, String>();//����һ��map���ڴ�Ŀ��·�����ļ������ƣ����ں�������ڽ��бȽ�
		for(int i=0;i<listDestination.size();i++){
			File t = new File(listDestination.get(i));
			dict.put(md5Generator.getMD5(t), listDestination.get(i));
			existfilesname.put(t.getName(), t.getName());
		}
//		System.out.println(existfilesname+"\r\n");
//		deleteRepeat.deleteRepeat(pathSource);//��ɾ��ԴĿ¼���ظ����ļ�
		ArrayList<String> listSource = filesArray.getFiles(pathSource);//���ڴ��Ŀ¼�����е��ļ�·��
		System.out.println("source file numbers:" + listSource.size());
		
		int mark=0;
		int repeat=0;
//		System.out.println(dict);
		for(int i=0;i<listSource.size();i++){
			File k = new File(listSource.get(i));
			String md5=md5Generator.getMD5(k);
//			System.out.println(md5);
//			System.out.println(existfilesname.get(k.getName()));
			

			if((dict.get(md5)==null) && (existfilesname.get(k.getName())!=null)){
				/*
				 * ����md5ֵ�Ƿ���Ŀ��Ŀ¼�д��ڣ������ļ�����Ŀ��Ŀ¼���Ƿ���ڣ���md5ֵ�����ڵ����ļ������ڣ�����Ҫ������
				 * 
				 */
				Path fromPath=Paths.get(listSource.get(i));
				Path toPath=Paths.get(pathDestination+"/"+df.format(new Date()).toString()+" "+k.getName());//Ŀ��Ŀ¼�е��ļ���������ǰ���������
				System.out.println("move "+fromPath+"---->"+toPath);
				try{
					Files.copy(fromPath,toPath); //���ڸ����ļ�����ɾ��ԭ�ļ�
//					Files.move(fromPath,toPath); //�����ƶ��ļ���ɾ��ԭ�ļ�
					mark++;
				}catch (IOException e){
					e.printStackTrace();
				}
				
			}
			else if((dict.get(md5)==null) && (existfilesname.get(k.getName())==null)) {
				/*
				 * ����md5ֵ�Ƿ���Ŀ��Ŀ¼�д��ڣ������ļ�����Ŀ��Ŀ¼���Ƿ���ڣ���md5ֵ���������ļ��������ڣ���ֱ���ƶ��ļ�
				 * 
				 */
				Path fromPath=Paths.get(listSource.get(i));
				Path toPath=Paths.get(pathDestination+"/"+k.getName());
				System.out.println("move "+fromPath+"---->"+toPath);
				try{
					Files.copy(fromPath,toPath); //���ڸ����ļ�����ɾ��ԭ�ļ�
//					Files.move(fromPath,toPath); //�����ƶ��ļ���ɾ��ԭ�ļ�
					existfilesname.put(k.getName(), k.getName()); //��������Ŀ¼������ļ����Ƽ�¼��Դ�ļ��п��ܲ�ͬ��Ŀ¼�¾�����ͬ���ļ�������md5��һ�������
					mark++;
				}catch (IOException e){
					e.printStackTrace();
				}
			}
			else if((dict.get(md5)!=null) ){
				repeat++;
			}
		}
		System.out.println("���ƶ� "+mark+" ���ļ���\r\n�� "+repeat+" ��md5ֵ����ļ��ظ���δ������\r\n");
		
		
		
	}
	
	public static void main(String[] args){
		merge("D:/��Ƭ","F:/������Ƭ"); //���ڲ���
	}

}
