package md5calculate;

import java.io.File;
//import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Date;

public class getFilename {
	public static void main(String[] args) {
		File t = new File("C:/Users/Unicom/Desktop/new/a.txt");
		System.out.println(t.getName());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        System.out.println(df.format(new Date())+" "+t.getName());// new Date()为获取当前系统时间
	}
}
