package myToolsTestNG;
/**
 * 
 * HoldelRefreshScrShot.java
 * Purpose:	To perform the following functions on a page :
 * 					** Check web page for Cross Country linkage ie .au links on .nz page and vice versa.
 * 					** Check Page title for each page for Cross Country linkage.
 * 					** Grab Body text and save in .doc files for each URL visited (MS Word's native Spell checking utility).
 * 					** Grab Disclaimer text and save in .doc files for each URL visited (MS Word's native Spell checking utility).
 * 					** Check Disclaimer text for each page for Cross Country linkage.
 * 			The Script, when executed, will create a unique folder for each run on that day. The location will be path\DDMmmYYYY\Run#N\Logs where path is a configurable variable.
 * A HTML report will be generated and will be displayed once the script is completed
 * Importing myMethods.* to utilise a list of existing methods eg Screenshots, page analyser etc.
 * 
 * @author Gaurav Sharma
 * @param myURL
 * @return HTML report, screenshots, console output and body and disclaimer text from each page tested (saved in data directory).
 * @version 0.1
 */

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
import org.openqa.selenium.firefox.FirefoxDriver;

import myMethods.*;

@SuppressWarnings("unused")
public class HoldenProjectTaskManagerResources {
	// Customise for each website
	//**********************************************************************************
	private static String myURL="https://taskmanager.visualjazz.com.au/Dashboard.aspx?view=holdenProjects";
	//private static String myURL="http://www.google.com";
	static String path="C:\\Output\\Screenshots\\HoldenProjectWork\\";
		//**********************************************************************************

	// no customisation below this point.


	private static String count;
	static String CurrentPath;
	static String UniqueURL_List =CurrentPath+"Logs\\FoundURLs.txt";
	static String ScreenshotResult;
	String ScreenshotResource;
	static WebDriver driver;
	String Name ;
	String status ;
	final static Stopwatch stopwatch = new Stopwatch().start();
	final static Stopwatch stopwatch1 = new Stopwatch().start();
	String Content;
	String nettValColor ;
	String nettVal;
	int nettVal_int;
	String colour;
	String title ;
	File myFile ;
	int URLCount=0;
	int ccflag=0;
	int j=0;

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




	@BeforeMethod
	public static void setUp() throws Exception {
		CurrentPath=SetUp.createDateFolder(path);
		ScreenshotResult=CurrentPath+"Screenshot.html";
		System.out.println("Setting up the browsers");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		WriteToFile.SOHTMLHPWR(ScreenshotResult);
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
		System.out.println(" ");
		AllURLs.add(myURL);// Starting URL added to array to initialise page crawl
		totalURLs.add(myURL);
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+myURL);
		for(int i=0; i<AllURLs.size(); i++) 
		{
			driver.get(AllURLs.get(i));
			try
			{
				Select option = new Select (driver.findElement(By.xpath("//*[@id=\"PlaceHolderBody_ddlProjectsPerPage\"]")));
				option.selectByValue("100");
				//Thread.sleep(10000);

			}
			catch (Exception E)
			{
				//null block;
			}
			Thread.sleep(500);
			System.out.println("Analysing Contents of page "+AllURLs.get(i)+"(No deep links)");
			WriteToFile.myWriteAppend(allAllURLs,"**Analysing Contents of page "+AllURLs.get(i));
			WriteToFile.myWriteAppend(console,"**Analysing Contents of page "+AllURLs.get(i));
			WriteToFile.myWriteAppend(allurls,"**Analysing Contents of page "+AllURLs.get(i));
			WriteToFile.myWriteAppend(allAllURLs," ");
			WriteToFile.myWriteAppend(console," ");
			WriteToFile.myWriteAppend(allurls," ");


			List<WebElement> HREFlist = null;
			Thread.sleep(500);
			// extracting all URLs on the initial page and iterating through them. not looking into deeplinks
			try {
				HREFlist = driver.findElements(By.cssSelector(" a"));
				for (WebElement link : HREFlist) 
				{
					browserurl=link.getAttribute("href");
					if (!(AllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains("https://taskmanager.visualjazz.com.au/ProjectInfo.aspx?task=view&id")) && !(browserurl.contentEquals(myURL+"/")) && !(browserurl.contains("#"))&& !(browserurl.contains(".pdf")) )
					{

						AllURLs.add(browserurl);
						WriteToFile.myWriteAppend(allAllURLs,"Link# "+AllURLs.size()+" "+browserurl);
					}


					//All URLs get stored here
					if (!(totalURLs.contains(browserurl)) && (browserurl!=null) && (browserurl!=""))
					{
						//
						totalURLs.add(browserurl);
						WriteToFile.myWriteAppend(allurls,"Link# "+totalURLs.size()+" "+browserurl);

					}


				}
			}

			catch (Exception e) 
			{
				System.out.println("No HREF's for this URL "+browserurl);
			}

			if(previousnonNativeurlcount == nonAllURLs.size())
			{
				WriteToFile.myWriteAppend(console,"No Cross Country Urls for this page");
				WriteToFile.myWriteAppend(console," ");	
			}
			if(previousnativeurlCount==AllURLs.size())
			{
				WriteToFile.myWriteAppend(allAllURLs,"No New or Unique Urls on this page");
				WriteToFile.myWriteAppend(allAllURLs," ");	
			}
			if(previoustotalcount==AllURLs.size())
			{
				WriteToFile.myWriteAppend(allurls,"No New or Unique Urls on this page");
				WriteToFile.myWriteAppend(allurls," ");	
			}

			WriteToFile.myWriteAppend(console," ");	
			WriteToFile.myWriteAppend(allAllURLs," ");	
			WriteToFile.myWriteAppend(allurls," ");	
			previousnonNativeurlcount=nonAllURLs.size();
			previousnativeurlCount=AllURLs.size();
			previoustotalcount=AllURLs.size();
			try
			{
			status=driver.findElement(By.xpath("//*[@id=\"PlaceHolderBody_lblProjectStatusText\"]")).getText();
			System.out.println(status);
			Name=driver.findElement(By.xpath("/html/body/form/div[3]/div/div[4]/h2")).getText();
			System.out.println(Name);
			nettValColor=driver.findElement(By.xpath("//*[@id=\"PlaceHolderBody_lblOverRun\"]")).getAttribute("Style");
			nettVal=driver.findElement(By.xpath("//*[@id=\"PlaceHolderBody_lblOverRun\"]")).getText();
			System.out.println("Colour "+nettValColor.replace("color: ", ""));
			System.out.println("Net Value is "+nettVal);
			nettVal=nettVal.replace("$","");
			nettVal=nettVal.replace(".","");
			nettVal=nettVal.replace(",","");
			System.out.println("Without the dollar symbol is : "+nettVal);
			nettVal_int=Integer.parseInt(nettVal);
			System.out.println("Now As an integer value is: $"+((float)nettVal_int/100));
			}
			catch(Exception E)
			{
				//NOthing to do here
			}
			
			
			try{
				
				Name=Name.replaceAll("&","_");
								
			}
			catch (Exception E)
			{
				//System.out.println("Nothing to replace");

			}
			try{
				Name=Name.replaceAll("-","_");
			}
			catch (Exception E)
			{
				//System.out.println("Nothing to replace");

			}
			try{
				Name=Name.replaceAll("=","_");
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
				Name=Name.replaceAll("/", "_");
				Name=Name.replaceAll("__", "_");
			}
			catch (Exception E)
			{
				//System.out.println("Nothing to replace");

			}
			// Adding table rows per url found

			//count=""+(i)+"";
			if ((i % 2)==0)
			{
				colour="B6B6B4";
			}
			else
			{
				colour="D1D0CE";

			}
			if(AllURLs.get(i).contains("https://taskmanager.visualjazz.com.au/ProjectInfo.aspx?task=view&id") && !(status.contains("Blue - Frozen")))
			{
				j=j+1;
				count=""+(j)+"";
				try {
					myFile= MyScreenshotTaker.Screenshot(driver);
					String Screenshot=CheckTitle.CheckErrorsComplete(driver, CurrentPath, AllURLs.get(i), count,Name, myFile);
					System.out.println(Screenshot);
					try{
					String resourceURL=driver.findElement(By.xpath("//*[@id=\"PlaceHolderBody_hypResources\"]")).getAttribute("href");
					driver.get(resourceURL);
					driver.findElement(By.xpath("//*[@id=\"PlaceHolderBody_lnkWeek12\"]")).click();
					while (driver.findElement(By.xpath("/html/body/form/div[3]/div[2]/span")).isDisplayed())
					{
						System.out.println("Waiting...");
					Thread.sleep(2000);
					}
					List<WebElement> resources = driver.findElements(By.xpath("//div/div/img[contains(@src,'plus.gif')]"));
					for(WebElement resource: resources)
					{
						resource.click();
						Thread.sleep(1000);
					}
					Thread.sleep(3000);
					myFile= MyScreenshotTaker.Screenshot(driver);
					ScreenshotResource=CheckTitle.CheckErrorsComplete(driver, CurrentPath, AllURLs.get(i), count,"Resources", myFile);
					System.out.println(ScreenshotResource);
					}
					catch (Exception E)
					{
						Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+AllURLs.get(i)+"\">"+Name+"</td></a><td><a href= \"Error/ErrorURL_List.txt\">No Screenshot</td><td><a href= \"Error/ErrorURL_List.txt\">No Resources</td></tr>"; 
					}
					Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+AllURLs.get(i)+"\">"+Name+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td><a href=\""+ScreenshotResource+"\"><img width=\"120\" height=\"70\" src=\""+ScreenshotResource+"\"></a></td></tr>";
					WriteToFile.myWriteAppend(ScreenshotResult,Content);
				}
				catch (Exception E)
				{	
				WriteToFile.myWriteAppend(ScreenshotResult,Content);
				System.out.println("Unable to take a screenshot");
				}
			}	
			System.out.println("Crawler now has: "+AllURLs.size()+" Urls");
			reportContent="";
		}

		//last layout with link to .txt files produced during execution
		WriteToFile.myWriteAppend(ScreenshotResult,"</table>");
		WriteToFile.myWriteAppend(ScreenshotResult,"</center>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<table border = \"0\" cellspacing = \"0\" cellpadding = \"10\"><tr>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Full page photo.pdf\"> View PDF Version</a></td>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Logs/SubUrls.txt\"> Links Extracted Per Page</a></td>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Logs/AllURLs.txt\"> All Extracted Links</td></tr></table>");
	}




	@AfterClass	

	public static void closeBrowser() throws Exception {
		long seconds=stopwatch.stop().elapsed(TimeUnit.SECONDS);
		WriteToFile.myWriteAppend(ScreenshotResult, " <br>");
		WriteToFile.myWriteAppend(ScreenshotResult, "<br>");
		WriteToFile.myWriteAppend(ScreenshotResult, "Time of execution in seconds: " +seconds);
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
