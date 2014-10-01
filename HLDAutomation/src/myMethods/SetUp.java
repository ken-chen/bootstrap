package myMethods;

/**
 * 
 * SetUp.java
 * Purpose:Create Parent Folder based on the date of execution in the path passed as a parameter.Subsequent 
 *         Unique "Run#XX" folders are created based on the number of runs that day.The path to the created 
 *         Run folder is then displayed in the console window.  
 *         
 * @author Gaurav Sharma
 * @params Path for the project is passed as a parameter.
 * @return Current Run for that project folder.
 * @version 1.0
 */




import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class SetUp {
	static DateFormat dateFormat = new SimpleDateFormat("ddMMMyyyy");
	static Date date = new Date();
	static int count=0;
	static String newdate = dateFormat.format(date).toString();
	public static String createDateFolder (String path) throws IOException {
		String FolderName=path+newdate;
		@SuppressWarnings("unused")
		boolean fireup = (new File(FolderName)).mkdirs();
		File Folder=new File(FolderName);
		count =Folder.list().length;
		String CurrentRun=FolderName+"\\Run#"+(count+1)+"\\";		
		@SuppressWarnings("unused")
		boolean fireup2 = (new File(CurrentRun)).mkdirs();
		System.out.println("Folder Created "+CurrentRun);
		return CurrentRun;
		}
	
}


