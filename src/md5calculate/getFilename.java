package md5calculate;

import java.io.File;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getFilename {
	public static void main(String[] args) {
		File t = new File("C:/Users/Unicom/Desktop/new/a.txt");
		System.out.println(t.getName());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
        System.out.println(df.format(new Date())+" "+t.getName());// new Date()Ϊ��ȡ��ǰϵͳʱ��
	}
}
