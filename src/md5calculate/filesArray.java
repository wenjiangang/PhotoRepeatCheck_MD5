package md5calculate;
import java.io.File;
import java.util.ArrayList;

/*
 * getFiles���������ļ���Ŀ¼������pathΪ�ļ�Ŀ¼������ֵ����ΪArrayList
 * 
 * 
 */

public class filesArray {
	public static ArrayList<String> getFiles(String path) {
	    ArrayList<String> files = new ArrayList<String>();
	    File file = new File(path);
	    File[] tempList = file.listFiles();

	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
	              System.out.println("file:" + tempList[i]);
	            files.add(tempList[i].toString());
	        }
	        if (tempList[i].isDirectory()) {
	              System.out.println("directory:" + tempList[i]); 
	              files.addAll(getFiles(tempList[i].toString()));
	        }
	    }
	    return files;
	}
	
	public static void main(String[] args){
		String pathTest="C:/Users/Unicom/Desktop/new"; //����Ŀ¼���ļ��Ƿ񶼱���ȡ����
		ArrayList<String> fileLists = getFiles(pathTest);
		System.out.println(fileLists+"\r\n"+fileLists.size());
		
		
	}

}
