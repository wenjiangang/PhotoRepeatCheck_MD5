package md5calculate;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import md5calculate.stampToDate;

/*
 * merge�������ںϲ������ļ��У�pathSourceΪԴĿ¼��pathDestinationΪ�ϲ����Ŀ��Ŀ¼
 * deleteRepeat.deleteRepeat(pathDestination)��ɾ��Ŀ¼���ظ����ļ�����ѡ
 * 
 * 
 */

public class mergeDirectory {
	public static void merge(String pathSource,String pathDestination){
/*
 * ����ԭʼ��merge������ÿ�����¼���md5ֵ�������ļ��϶�����ļ��ϴ��������ܻỨ�ѽ϶�ʱ��
 */
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
				Path toPath=Paths.get(pathDestination+"/"+stampToDate.stamptodate(k.lastModified())+" "+k.getName());//Ŀ��Ŀ¼�е��ļ���������ǰ������ļ��޸ĵ�����
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
	
	public static void mergebylog(String pathSource,String pathDestination){
/*
 * ���ǸĽ����merge������ͨ��Ԥ���ж��ļ��е�md5log�����кϲ�������ʹ�õ��ϴ����ɵ�md5log.txt�Ա��ʡʱ��
 * 
 */
		HashMap<String, String> dict=new HashMap<String, String>();//����һ��map���ڴ��Ŀ��·���µ�md5��Ͷ�Ӧ���ļ�·��
		HashMap<String, String> existfilesname=new HashMap<String, String>();//����һ��map���ڴ�Ŀ��·�����ļ������ƣ����ں�������ڽ��бȽ�
		try {
			File writeName = new File(pathDestination+"/md5log.txt");
			if (!writeName.exists()) {
				writeName.createNewFile(); // ��������ڵĻ��������ļ�
//				deleteRepeat.deleteRepeat(pathDestination);//��ɾ��Ŀ��Ŀ¼���ظ����ļ�
				ArrayList<String> listDestination = filesArray.getFiles(pathDestination);//���ڴ��Ŀ¼�����е��ļ�·��
				System.out.println("destination file numbers:" + listDestination.size());

				for(int i=0;i<listDestination.size();i++){
					File t = new File(listDestination.get(i));
//					dict.put(md5Generator.getMD5(t), listDestination.get(i));
					readLog.writeFile(pathDestination+"/md5log.txt", md5Generator.getMD5(t)+"="+ t.getName());
					existfilesname.put(t.getName(), t.getName());
				}
				dict=readLog.readFile(pathDestination+"/md5log.txt");//��ȡmd5log�ļ��е����ݴ���dict
			}else if(writeName.exists()) {
				ArrayList<String> listDestination = filesArray.getFiles(pathDestination);//���ڴ��Ŀ¼�����е��ļ�·��
				System.out.println("destination file numbers:" + listDestination.size());
				for(int i=0;i<listDestination.size();i++){
					File t = new File(listDestination.get(i));
					existfilesname.put(t.getName(), t.getName());
				}
				dict=readLog.readFile(pathDestination+"/md5log.txt");//��ȡmd5log�ļ��е����ݴ���dict
			}

		} catch (IOException e) {
			e.printStackTrace();
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
				Path toPath=Paths.get(pathDestination+"/"+stampToDate.stamptodate(k.lastModified())+" "+k.getName());//Ŀ��Ŀ¼�е��ļ���������ǰ������ļ��޸ĵ�����
				System.out.println("move "+fromPath+"---->"+toPath);
				try{
					Files.copy(fromPath,toPath); //���ڸ����ļ�����ɾ��ԭ�ļ�
//					Files.move(fromPath,toPath); //�����ƶ��ļ���ɾ��ԭ�ļ�
					mark++;
					readLog.writeFile(pathDestination+"/md5log.txt", md5+"="+ stampToDate.stamptodate(k.lastModified())+" "+k.getName());
					
					
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
					readLog.writeFile(pathDestination+"/md5log.txt", md5+"="+k.getName());
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
		mergebylog("D:/��Ƭ","J:/������Ƭ"); //���ڲ���
	}

}
