package md5calculate;
import java.io.File;
import java.util.ArrayList;

public class filesArray {
	public static ArrayList<String> getFiles(String path) {
	    ArrayList<String> files = new ArrayList<String>();
	    File file = new File(path);
	    File[] tempList = file.listFiles();

	    for (int i = 0; i < tempList.length; i++) {
	        if (tempList[i].isFile()) {
//	              System.out.println("��     ����" + tempList[i]);
	            files.add(tempList[i].toString());
	        }
//	        if (tempList[i].isDirectory()) {
//	              System.out.println("�ļ��У�" + tempList[i]);
//	        }
	    }
	    return files;
	}

}
