package myMethods;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import myMethods.WriteToFile;
/**
 * 
 * CheckTitle.java
 * Purpose:  
 *         
 * @author Gaurav Sharma
 * @params 
 * @return Current Run for that project folder.
 * @version 1.0
 */

public class CheckTitle {

	public static String CheckErrorsComplete (WebDriver mydriver,String path, String CurrURL, String Count,String name, File File) throws IOException {
		String URL=CurrURL;
		WebDriver driver = mydriver;
		File myFile=File;
		String CurrentPath=path;
		String ErrorLog=(CurrentPath+"Error\\ErrorURL_List.txt");
		String count=Count;
		String Name= name;
		String title=mydriver.getTitle();
		
		if (title.contentEquals("404"))
		{
			System.out.println("** 404 Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**404 Error on Record # "+count+" "+URL );   	
		}
		else if (title.contentEquals("500"))
		{
			System.out.println("** 500 Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**500 Error on Record # "+count+" "+URL); 
		}
		else if (title.contains("Could not load file"))
		{
			System.out.println("** Could not load file Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**Could not load file Error on Record # "+count+" "+URL); 
		}
		else if (title.contentEquals("Problem loading page"))
		{
			System.out.println("** Redirection Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**Redirection Error on Record # "+count+" "+URL); 
		}
		else if (title.contentEquals("Error Message"))
		{
			System.out.println("** Network Access Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**Network Access Error on Record # "+count+" "+URL); 
		}
		else if (!(driver.getCurrentUrl().toString().contentEquals(URL)))
		{
			System.out.println("** Redirected from "+URL+"to "+driver.getCurrentUrl().toString());
			WriteToFile.myWriteAppend(ErrorLog,"**Redirected from "+URL+"to "+driver.getCurrentUrl().toString()); 
		}
		else 
		{
			WriteToFile.myWriteAppend(ErrorLog,"No Errors Found on "+URL); 
		}
		WriteToFile.myWriteAppend(ErrorLog,""); 
		WriteToFile.myWriteAppend(ErrorLog,"");
		System.out.println("Screenshot for URL "+URL+" saved in Folder: "+CurrentPath);
		FileUtils.copyFile(myFile, new File(CurrentPath+count+Name +"_Screenshot.jpg"));
		return (CurrentPath+count+Name +"_Screenshot.jpg");

	}

	public static void CheckErrorsDistributed (WebDriver mydriver,String path, String CurrURL, String Count,String name, File File) throws IOException {
		String URL=CurrURL;
		File myFile=File;
		String CurrentPath=path;
		String ErrorLog=CurrentPath+"Error\\ErrorURL_List.txt";
		String count=Count;
		String Name= name;
		String title=mydriver.getTitle();
		if (title.contentEquals("404"))
		{
			System.out.println("** 404 Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**404 Error on Record # "+count+" "+URL );   	
		}
		else if (title.contentEquals("500"))
		{
			System.out.println("** 500 Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**500 Error on Record # "+count+" "+URL); 
		}
		else if (title.contentEquals("Problem loading page"))
		{
			System.out.println("** Redirection Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**Redirection Error on Record # "+count+" "+URL); 
		}
		else if (title.contentEquals("Error Message"))
		{
			System.out.println("** Network Access Error on "+URL+" URL saved in Errorlog");
			WriteToFile.myWriteAppend(ErrorLog,"**Network Access Error on Record # "+count+" "+URL); 
		}

		System.out.println("Screenshot for URL "+URL+" saved in Folder "+CurrentPath);
		FileUtils.copyFile(myFile, new File(CurrentPath+URL+"\\"+Name +"_test.jpg"));

	}

}


