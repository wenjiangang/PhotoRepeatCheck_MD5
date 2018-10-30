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
 * merge方法用于合并两个文件夹，pathSource为源目录，pathDestination为合并后的目标目录
 * deleteRepeat.deleteRepeat(pathDestination)先删除目录下重复的文件，可选
 * 
 * 
 */

public class mergeDirectory {
	public static void merge(String pathSource,String pathDestination){
/*
 * 这是原始的merge方法，每次重新计算md5值，对于文件较多或者文件较大的情况可能会花费较多时间
 */
//		deleteRepeat.deleteRepeat(pathDestination);//先删除目标目录下重复的文件
		ArrayList<String> listDestination = filesArray.getFiles(pathDestination);//用于存放目录下所有的文件路径
		System.out.println("destination file numbers:" + listDestination.size());
		HashMap<String, String> dict=new HashMap<String, String>();//创建一个map用于存放目标路径下的md5码和对应的文件路径
		HashMap<String, String> existfilesname=new HashMap<String, String>();//创建一个map用于存目标路径下文件的名称，用于后期与后期进行比较
		for(int i=0;i<listDestination.size();i++){ //遍历文件目录中所有的文件
			File t = new File(listDestination.get(i));
			dict.put(md5Generator.getMD5(t), listDestination.get(i));//将md5值和文件名存入dict
			existfilesname.put(t.getName().toLowerCase(), "1");//将文件名和“1”存入existfilesname，“1”没有实际意义
		}
//		deleteRepeat.deleteRepeat(pathSource);//先删除源目录下重复的文件
		ArrayList<String> listSource = filesArray.getFiles(pathSource);//用于存放目录下所有的文件路径
		System.out.println("source file numbers:" + listSource.size());
		
		int mark=0;
		int repeat=0;
		for(int i=0;i<listSource.size();i++){
			File k = new File(listSource.get(i));
			String md5=md5Generator.getMD5(k);
			if((dict.get(md5)==null) && (existfilesname.get(k.getName().toLowerCase())!=null)){
				/*
				 * 检查该md5值是否在目标目录中存在，并且文件名在目标目录中是否存在，若md5值不存在但是文件名存在，则需要重命名
				 * 
				 */
				Path fromPath=Paths.get(listSource.get(i));
				Path toPath=Paths.get(pathDestination+"/"+stampToDate.stamptodate(k.lastModified())+" "+k.getName());//目标目录中的文件名重命名前面加上文件修改的日期
				System.out.println("move "+fromPath+"---->"+toPath);
				try{
					Files.copy(fromPath,toPath); //用于复制文件，不删除原文件
//					Files.move(fromPath,toPath); //用于移动文件，删除原文件
					mark++;
					dict.put(md5,stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase());
					existfilesname.put(stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase(), "1");
				}catch (IOException e){
					e.printStackTrace();
				}
				
			}
			else if((dict.get(md5)==null) && (existfilesname.get(k.getName().toLowerCase())==null)) {
				/*
				 * 检查该md5值是否在目标目录中存在，并且文件名在目标目录中是否存在，若md5值不存在且文件名不存在，则直接移动文件
				 * 
				 */
				Path fromPath=Paths.get(listSource.get(i));
				Path toPath=Paths.get(pathDestination+"/"+k.getName());
				System.out.println("move "+fromPath+"---->"+toPath);
				try{
					Files.copy(fromPath,toPath); //用于复制文件，不删除原文件
//					Files.move(fromPath,toPath); //用于移动文件，删除原文件
					mark++;
					dict.put(md5,k.getName().toLowerCase());
					existfilesname.put(k.getName().toLowerCase(), "1");//用于在新目录下添加文件名称记录，源文件中可能不同的目录下具有相同的文件名但是md5不一样的情况
				}catch (IOException e){
					e.printStackTrace();
				}
			}
			else if((dict.get(md5)!=null) ){
				repeat++;
			}
		}
		System.out.println("共移动 "+mark+" 份文件！\r\n有 "+repeat+" 份md5值检测文件重复，未做处理！\r\n");
		
		
		
	}
	
	public static void mergebylog(String pathSource,String pathDestination){
/*
 * 这是改进后的merge方法，通过预先判断文件中的md5log来进行合并，可以使用到上次生成的md5log.txt以便节省时间
 * 
 */
		HashMap<String, String> dict=new HashMap<String, String>();//创建一个map用于存放目标路径下的md5码和对应的文件路径
		HashMap<String, String> existfilesname=new HashMap<String, String>();//创建一个map用于存目标路径下文件的名称，用于后期与后期进行比较
		try {
			File logname = new File(pathDestination+"/md5log.txt");
			if (!logname.exists()) {
				logname.createNewFile(); // 如果不存在的话创建新文件
//				deleteRepeat.deleteRepeat(pathDestination);//先删除目标目录下重复的文件
				ArrayList<String> listDestination = filesArray.getFiles(pathDestination);//用于存放目录下所有的文件路径
				System.out.println("destination file numbers:" + listDestination.size());

				for(int i=0;i<listDestination.size();i++){
					File t = new File(listDestination.get(i));
//					dict.put(md5Generator.getMD5(t), listDestination.get(i));
					readLog.writeFile(pathDestination+"/md5log.txt", md5Generator.getMD5(t)+"="+ t.getName().toLowerCase());//将新文件的md值和文件名小写存入log文本
					existfilesname.put(t.getName().toLowerCase(), "1");
				}
				dict=readLog.readFile(pathDestination+"/md5log.txt");//读取md5log文件中的数据存入dict
				
			}else if(logname.exists()) {
				ArrayList<String> listDestination = filesArray.getFiles(pathDestination);//用于存放目录下所有的文件路径
				System.out.println("destination file numbers:" + listDestination.size());
				dict=readLog.readFile(pathDestination+"/md5log.txt");//读取md5log文件中的数据存入dict
				Collection<String> values=dict.values();
//				System.out.println(existfilesname);
				for(Object object:values) {
					existfilesname.put(object.toString().toLowerCase(), "1");
//					
				}
				
				for(int i=0;i<listDestination.size();i++){
					File t = new File(listDestination.get(i));
					if(existfilesname.get(t.getName().toLowerCase())==null) { //这一步是为了防止用户直接将文件拷贝到目标目录而在md5log文件中没有生产md5值，因此先进行判断
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
//		deleteRepeat.deleteRepeat(pathSource);//先删除源目录下重复的文件
		ArrayList<String> listSource = filesArray.getFiles(pathSource);//用于存放目录下所有的文件路径
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
				 * 检查该md5值是否在目标目录中存在，并且文件名在目标目录中是否存在，若md5值不存在但是文件名存在，则需要重命名
				 * 
				 */
				Path fromPath=Paths.get(listSource.get(i));
				Path toPath=Paths.get(pathDestination+"/"+stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName());//目标目录中的文件名重命名前面加上文件修改的日期
				System.out.println("move "+fromPath+"---->"+toPath);
				try{
					Files.copy(fromPath,toPath); //用于复制文件，不删除原文件
//					Files.move(fromPath,toPath); //用于移动文件，删除原文件
					mark++;
					readLog.writeFile(pathDestination+"/md5log.txt", md5+"="+ stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase());
					dict.put(md5,stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase());//往dict中存入新的文件信息
					existfilesname.put(stampToDate.stamptodate(k.lastModified()).toLowerCase()+" "+k.getName().toLowerCase(), "1");
					
				}catch (IOException e){
					e.printStackTrace();
				}
				
			}
			
			else if((dict.get(md5)==null) && (existfilesname.get(k.getName())==null)) {
				/*
				 * 检查该md5值是否在目标目录中存在，并且文件名在目标目录中是否存在，若md5值不存在且文件名不存在，则直接移动文件
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
					Files.copy(fromPath,toPath); //用于复制文件，不删除原文件
//					Files.move(fromPath,toPath); //用于移动文件，删除原文件
					existfilesname.put(k.getName(), k.getName()); //用于在新目录下添加文件名称记录，源文件中可能不同的目录下具有相同的文件名但是md5不一样的情况
					mark++;
					readLog.writeFile(pathDestination+"/md5log.txt", md5+"="+k.getName());
					dict.put(md5,k.getName());//往dict中存入新的文件信息
				}catch (IOException e){
					e.printStackTrace();
				}
			}
			else if((dict.get(md5)!=null) ){
				repeat++;
			}
		}
		System.out.println("共移动 "+mark+" 份文件！\r\n有 "+repeat+" 份md5值检测文件重复，未做处理！\r\n");
		
		
		
	}
	
	public static void main(String[] args){
		mergebylog("C:/Users/jacky/Desktop/冲洗照片","F:/所有照片"); //用于测试
	}

}
