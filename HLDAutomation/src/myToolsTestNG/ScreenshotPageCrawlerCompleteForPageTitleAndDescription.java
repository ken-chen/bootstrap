package myToolsTestNG;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Stopwatch;
//import org.openqa.selenium.firefox.FirefoxDriver;

import myMethods.*;

/**
 * @author kchen
 * 
 * Created for report the title and description
 *
 */
@SuppressWarnings("unused")
public class ScreenshotPageCrawlerCompleteForPageTitleAndDescription {
	// Customise for each website
	//**********************************************************************************
	private static String myURL="http://preprod.holden.com.au/cars/colorado";
	static String path="C:\\Output\\Screenshots\\";
	static String tracking_id ="UA-XXXXXXXX-X"; // customise for each site 
	static String GTM_ID="GTM-XXXXX"; // can be customised
	static String GTMUA_ID="UA-XXXXXX-XX";//can be customised
	//**********************************************************************************

	// no customisation below this point.


	private static String count;
	String GTMUA_scriptdata;
	String scriptdata;
	static String CurrentPath;
	static String UniqueURL_List =CurrentPath+"Logs\\FoundURLs.txt";
	/**
	 * 
	 */
	static String errorlog =CurrentPath+"Error\\ErrorURL_List.txt";
	static String ScreenshotResult;
	static WebDriver driver;
	String Name ;
	final static Stopwatch stopwatch = new Stopwatch().start();
	final static Stopwatch stopwatch1 = new Stopwatch().start();
	String Content;
	String colour;
	String title ;
	File myFile ;
	int URLCount=0;
	int ccflag=0;

	// Declared Variables 
	static String allAllURLs;
	static String console;
	static String allurls;
	static String pagedata;
	String browserurl;
	int disclaimercount;
	int previoustotalcount=0;
	int previousnativeurlCount=0;
	int previousnonNativeurlcount=0;
	List<String> AllURLs = new ArrayList<String>();
	List<String> totalURLs = new ArrayList<String>();
	String reportContent=""; 
	List<String> nonAllURLs = new ArrayList<String>();
	List<WebElement> MetaSEO = new ArrayList<WebElement>();




	@BeforeMethod

	public static void setUp() throws Exception {
		CurrentPath=SetUp.createDateFolder(path);
		errorlog =CurrentPath+"Error\\ErrorURL_List.txt";
		ScreenshotResult=CurrentPath+"Screenshot.html";
		System.out.println("Setting up the browsers");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		WriteToFile.SOHTMLGENERAL(ScreenshotResult,myURL);
		WriteToFile.myWriteSOF(errorlog);
		// Directory Setup as per path variable.
		allAllURLs =CurrentPath+"Logs\\SubUrls.txt";
		console =CurrentPath+"Logs\\Console_log.txt";
		allurls =CurrentPath+"Logs\\AllURLs.txt";
		// Setting up Files.
		WriteToFile.myWriteSOF(allAllURLs);
		WriteToFile.myWriteSOF(console);
		WriteToFile.myWriteSOF(allurls);
	}


	@Test
	public void Test_Browser() throws Exception {
		
		int flag =0;

		if (myURL.contains(".com.au"))
		{
			flag =1;

	
		}
		
		AllURLs.add(myURL);// Starting URL added to array to initialise page crawl
		totalURLs.add(myURL);
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+myURL);

		for(int i=0; i<AllURLs.size(); i++) 
		{
			driver.get(AllURLs.get(i));
			Thread.sleep(2000);
			WriteToFile.myWriteAppend(errorlog,"Scanning for 404,500, Redirection and network errors on :"+AllURLs.get(i)); // functionality in CheckTitle.CheckErrorsComplete Method
			System.out.println("Analysing Contents of page "+AllURLs.get(i)+"(No deep links)");
			WriteToFile.myWriteAppend(allAllURLs,"**Analysing Contents of page "+AllURLs.get(i));
			WriteToFile.myWriteAppend(console,"**Analysing Contents of page "+AllURLs.get(i));
			WriteToFile.myWriteAppend(allurls,"**Analysing Contents of page "+AllURLs.get(i));
			WriteToFile.myWriteAppend(allAllURLs," ");
			WriteToFile.myWriteAppend(console," ");
			WriteToFile.myWriteAppend(allurls," ");

			// Page text grab 
			String PageSnapShot=CurrentPath+"Logs\\"+(AllURLs.get(i).replace(myURL,"WebPage_")).replace("/", "_").replace("?","_").replace("__","_")+".doc";
			WriteToFile.myWriteSOF(PageSnapShot);
			
			try {
				pagedata= driver.findElement(By.cssSelector(" body")).getText();
				WriteToFile.myWriteAppend(PageSnapShot,pagedata);
			} catch (Exception e) {
				System.out.println("No boby text ! "+ e.getMessage());
			}



			WriteToFile.myWriteEOF(PageSnapShot);
			try {
				System.out.println("Page title correct and is " +driver.getTitle());
				reportContent=reportContent+"<Strong>Page title is</Strong> :<br>"+driver.getTitle()+"<br>";
				WriteToFile.myWriteAppend(console,"Page title is " +driver.getTitle() );
				WriteToFile.myWriteAppend(console,"");
			} catch (Exception e) {
				System.out.println("No Page title! " +e.getMessage());
				WriteToFile.myWriteAppend(console,"Page title is Empty.");
				WriteToFile.myWriteAppend(console,"");
			}


			
			List<WebElement> HREFlist = null;
			Thread.sleep(1000);
			// extracting all URLs on the initial page and iterating through them. not looking into deeplinks
			try {
				HREFlist = driver.findElements(By.cssSelector(" a"));
				for (WebElement link : HREFlist) 
				{
					browserurl=link.getAttribute("href");
					if (!(AllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.startsWith(myURL)) && !(browserurl.contentEquals(myURL+"/")) && !(browserurl.contains(".pdf")) && !(browserurl.contains("#")) )
					{

						AllURLs.add(browserurl);
					}


					// AU links on NZ sites get stored here
					if (flag==0)
					{
						if (!(nonAllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(".com.au")))
						{
							nonAllURLs.add(browserurl);
							ccflag=1;

						}

					}

					// NZ links on AU site get stored here
					if (flag==1)
					{
						if (!(nonAllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(".co.nz")))
						{
							nonAllURLs.add(browserurl);
							ccflag=1;
						}
					}

					//All URLs get stored here
					if (!(totalURLs.contains(browserurl)) && (browserurl!=null) && (browserurl!=""))
					{
						//
						totalURLs.add(browserurl);
						

					}


				}
			}

			catch (Exception e) 
			{
				System.out.println("No HREF's for this URL "+browserurl);
			}


			ccflag=0;


			WriteToFile.myWriteAppend(console," ");	
			WriteToFile.myWriteAppend(allAllURLs," ");	
			WriteToFile.myWriteAppend(allurls," ");	
			previousnonNativeurlcount=nonAllURLs.size();
			previousnativeurlCount=AllURLs.size();
			previoustotalcount=AllURLs.size();
			Name=AllURLs.get(i).replace(myURL,"MainURL");
			Name=Name.replaceAll("/", "_");
			Name=Name.replaceAll("__", "_");
			try{
				Name=Name.replaceAll("#","_");
			}
			catch (Exception E)
			{
				//System.out.println("Nothing to replace");

			}
			try{
				Name=Name.replaceAll("%", "_");
			}
			catch (Exception E)
			{
				//System.out.println("Nothing to replace");

			}
			
			try{
				Name=Name.replaceAll("&", "_");
			}
			catch (Exception E)
			{
				//System.out.println("Nothing to replace");

			}
			try{
				Name=Name.replace("?","_");
			}
			catch (Exception E)
			{
				//System.out.println("Nothing to replace");

			}
			try{
				Name=Name.replaceAll("__", "_");
			}
			catch (Exception E)
			{
				//System.out.println("Nothing to replace");

			}
			
			
			//Meta Description Extraction
			try
			{
				MetaSEO=driver.findElements(By.xpath("//meta[contains(@name,'description')]")) ;
				if(MetaSEO.size()==0)
				{
					reportContent=reportContent+"<Font color =\"red\"><b>No Description Found</b></font><br>";				
				}
				else
				{	
					
					reportContent=reportContent+"<b>Page description is:</b><br>";
					for( WebElement metadata : MetaSEO)
					{
							
								reportContent=reportContent+"\n"+ metadata.getAttribute("Content")+"<br>" ;
							
					}
					
				}
			}
			catch(Exception E)
			{
				reportContent=reportContent+"<Font color =\"red\"><b>Error Extracting Description  Data</b></font><br>";				
			}



			// Adding table rows per url found

			count=""+(i+1)+"";
			if ((i % 2)==0)
			{
				colour="B6B6B4";
			}
			else
			{
				colour="D1D0CE";

			}
			try {
				myFile= MyScreenshotTaker.Screenshot(driver);
				String Screenshot=CheckTitle.CheckErrorsComplete(driver, CurrentPath, AllURLs.get(i), count,Name, myFile);
				Screenshot=Screenshot.replace(CurrentPath, "");
				PageSnapShot=PageSnapShot.replace(CurrentPath, "");
				Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+AllURLs.get(i)+"\">"+AllURLs.get(i)+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">Text Grab</a></td></tr>";
				WriteToFile.myWriteAppend(ScreenshotResult,Content);
			}
			catch (Exception E)
			{	Content="<tr><td>"+count+"</td><td><a href = \""+AllURLs.get(i)+"\">"+AllURLs.get(i)+"</td></a><td><a href= \"Error/ErrorURL_List.txt\">No Screenshot</td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">Text Grab</a></td></tr>"; 
			WriteToFile.myWriteAppend(ScreenshotResult,Content);
			System.out.println("Unable to take a screenshot");
			}
			System.out.println("Crawler now has: "+AllURLs.size()+" Urls");
			reportContent="";
		}

		//last layout with link to .txt files produced during execution
		WriteToFile.myWriteAppend(ScreenshotResult,"</table>");
		WriteToFile.myWriteAppend(ScreenshotResult,"</center>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<table border = \"0\" cellspacing = \"0\" cellpadding = \"10\"><tr>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Error/ErrorURL_List.txt\"> View Url Errors</a></td>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Logs/Console_log.txt\"> View Page Errors</a></td>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Logs/SubUrls.txt\"> Links Extracted Per Page</a></td>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Logs/AllURLs.txt\"> All Extracted Links</td></tr></table>");
	}




	@AfterClass

	public static void closeBrowser() throws Exception {
		long seconds=stopwatch.stop().elapsed(TimeUnit.SECONDS);
		WriteToFile.myWriteAppend(ScreenshotResult, " <br>");
		WriteToFile.myWriteAppend(ScreenshotResult, "<br>");
		WriteToFile.myWriteAppend(ScreenshotResult, "Time of execution in seconds: " +seconds);
		WriteToFile.myWriteEOF(errorlog);
		WriteToFile.myWriteAppend(console, "Time of execution in Seconds: " +seconds+" Seconds"); 
		WriteToFile.myWriteAppend(allurls, "Time of execution in Seconds: " +seconds+" Seconds"); 
		WriteToFile.myWriteEOF(console);
		WriteToFile.myWriteEOF(allurls);
		WriteToFile.EOHTMLGEN(ScreenshotResult);
		File htmlFile = new File(ScreenshotResult);
		Desktop.getDesktop().browse(htmlFile.toURI());
		driver.quit();
		System.out.println("Shutting down the browser");
		System.out.println("Time of execution in Seconds: " +seconds );
	}

}
