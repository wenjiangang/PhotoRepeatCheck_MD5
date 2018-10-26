package md5calculate;

import java.text.SimpleDateFormat;
import java.util.Date;

public class stampToDate {
	public static String stamptodate(Long s){
	    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
	    Date date = new Date(s);
	    return simpleDateFormat.format(date);
	}
	

}
