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
import java.util.Collection;
import java.util.HashMap;

import javafx.print.Collation;
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
		for(int i=0;i<listDestination.size();i++){ //�����ļ�Ŀ¼�����е��ļ�
			File t = new File(listDestination.get(i));
			dict.put(md5Generator.getMD5(t), listDestination.get(i));//��md5ֵ���ļ�������dict
			existfilesname.put(t.getName().toLowerCase(), "1");//���ļ����͡�1������existfilesname����1��û��ʵ������
		}
//		deleteRepeat.deleteRepeat(pathSource);//��ɾ��ԴĿ¼���ظ����ļ�
		ArrayList<String> listSource = filesArray.getFiles(pathSource);//���ڴ��Ŀ¼�����е��ļ�·��
		System.out.println("source file numbers:" + listSource.size());
		
		int mark=0;
		int repeat=0;
		for(int i=0;i<listSource.size();i++){
			File k = new File(listSource.get(i));
			String md5=md5Generator.getMD5(k);
			if((dict.get(md5)==null) && (existfilesname.get(k.getName().toLowerCase())!=null)){
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
					dict.put(md5,stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase());
					existfilesname.put(stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase(), "1");
				}catch (IOException e){
					e.printStackTrace();
				}
				
			}
			else if((dict.get(md5)==null) && (existfilesname.get(k.getName().toLowerCase())==null)) {
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
					mark++;
					dict.put(md5,k.getName().toLowerCase());
					existfilesname.put(k.getName().toLowerCase(), "1");//��������Ŀ¼������ļ����Ƽ�¼��Դ�ļ��п��ܲ�ͬ��Ŀ¼�¾�����ͬ���ļ�������md5��һ�������
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
			File logname = new File(pathDestination+"/md5log.txt");
			if (!logname.exists()) {
				logname.createNewFile(); // ��������ڵĻ��������ļ�
//				deleteRepeat.deleteRepeat(pathDestination);//��ɾ��Ŀ��Ŀ¼���ظ����ļ�
				ArrayList<String> listDestination = filesArray.getFiles(pathDestination);//���ڴ��Ŀ¼�����е��ļ�·��
				System.out.println("destination file numbers:" + listDestination.size());

				for(int i=0;i<listDestination.size();i++){
					File t = new File(listDestination.get(i));
//					dict.put(md5Generator.getMD5(t), listDestination.get(i));
					readLog.writeFile(pathDestination+"/md5log.txt", md5Generator.getMD5(t)+"="+ t.getName().toLowerCase());//�����ļ���mdֵ���ļ���Сд����log�ı�
					existfilesname.put(t.getName().toLowerCase(), "1");
				}
				dict=readLog.readFile(pathDestination+"/md5log.txt");//��ȡmd5log�ļ��е����ݴ���dict
				
			}else if(logname.exists()) {
				ArrayList<String> listDestination = filesArray.getFiles(pathDestination);//���ڴ��Ŀ¼�����е��ļ�·��
				System.out.println("destination file numbers:" + listDestination.size());
				dict=readLog.readFile(pathDestination+"/md5log.txt");//��ȡmd5log�ļ��е����ݴ���dict
				Collection<String> values=dict.values();
//				System.out.println(existfilesname);
				for(Object object:values) {
					existfilesname.put(object.toString().toLowerCase(), "1");
//					
				}
				
				for(int i=0;i<listDestination.size();i++){
					File t = new File(listDestination.get(i));
					if(existfilesname.get(t.getName().toLowerCase())==null) { //��һ����Ϊ�˷�ֹ�û�ֱ�ӽ��ļ�������Ŀ��Ŀ¼����md5log�ļ���û������md5ֵ������Ƚ����ж�
						readLog.writeFile(pathDestination+"/md5log.txt", md5Generator.getMD5(t)+"="+ t.getName().toLowerCase());
						dict.put(md5Generator.getMD5(t), t.getName().toLowerCase());
						existfilesname.put(t.getName().toLowerCase(), "1");
					}
				}
				
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
//			System.out.println(k.getName());
//			System.out.println(existfilesname.get(k.getName()));
//			System.out.println(existfilesname.get("IMG_0471.jpg"));
//			System.out.println(existfilesname);

			if((dict.get(md5)==null) && (existfilesname.get(k.getName().toLowerCase())!=null)){
				/*
				 * ����md5ֵ�Ƿ���Ŀ��Ŀ¼�д��ڣ������ļ�����Ŀ��Ŀ¼���Ƿ���ڣ���md5ֵ�����ڵ����ļ������ڣ�����Ҫ������
				 * 
				 */
				Path fromPath=Paths.get(listSource.get(i));
				Path toPath=Paths.get(pathDestination+"/"+stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName());//Ŀ��Ŀ¼�е��ļ���������ǰ������ļ��޸ĵ�����
				System.out.println("move "+fromPath+"---->"+toPath);
				try{
					Files.copy(fromPath,toPath); //���ڸ����ļ�����ɾ��ԭ�ļ�
//					Files.move(fromPath,toPath); //�����ƶ��ļ���ɾ��ԭ�ļ�
					mark++;
					readLog.writeFile(pathDestination+"/md5log.txt", md5+"="+ stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase());
					dict.put(md5,stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase());//��dict�д����µ��ļ���Ϣ
					existfilesname.put(stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase(), "1");
					
				}catch (IOException e){
					e.printStackTrace();
				}
				
			}
			
			else if((dict.get(md5)==null) && (existfilesname.get(k.getName())==null)) {
				/*
				 * ����md5ֵ�Ƿ���Ŀ��Ŀ¼�д��ڣ������ļ�����Ŀ��Ŀ¼���Ƿ���ڣ���md5ֵ���������ļ��������ڣ���ֱ���ƶ��ļ�
				 * 
				 */
//				System.out.println(existfilesname);
				System.out.println(existfilesname.get("IMG_0471.jpg"));
				System.out.println(existfilesname.get("IMG_20150828_070541.jpg"));
				System.out.println("----------------------------------------");
				Path fromPath=Paths.get(listSource.get(i));
				Path toPath=Paths.get(pathDestination+"/"+k.getName());
				System.out.println("move "+fromPath+"---->"+toPath);
				try{
					Files.copy(fromPath,toPath); //���ڸ����ļ�����ɾ��ԭ�ļ�
//					Files.move(fromPath,toPath); //�����ƶ��ļ���ɾ��ԭ�ļ�
					existfilesname.put(k.getName(), k.getName()); //��������Ŀ¼������ļ����Ƽ�¼��Դ�ļ��п��ܲ�ͬ��Ŀ¼�¾�����ͬ���ļ�������md5��һ�������
					mark++;
					readLog.writeFile(pathDestination+"/md5log.txt", md5+"="+k.getName());
					dict.put(md5,k.getName());//��dict�д����µ��ļ���Ϣ
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
		mergebylog("C:/Users/jacky/Desktop/��ϴ��Ƭ","F:/������Ƭ"); //���ڲ���
	}

}
