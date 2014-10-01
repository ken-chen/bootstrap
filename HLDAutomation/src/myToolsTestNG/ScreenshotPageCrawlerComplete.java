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
//import org.openqa.selenium.firefox.FirefoxDriver;

import myMethods.*;

@SuppressWarnings("unused")
public class ScreenshotPageCrawlerComplete {
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

			if(myURL.contains("holden"))
			{	

				tracking_id="UA-5073957-46";
				GTM_ID="GTM-FB9K";
				GTMUA_ID="UA-42561492-2";
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-8798612-16";
					GTM_ID="GTM-JPHVT";
					GTMUA_ID="UA-42561492-2";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-1";
					GTM_ID="GTM-L86JS";
					GTMUA_ID="UA-7653497-20";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-16";
					GTM_ID="GTM-4PBX9";
					GTMUA_ID="UA-7653497-20";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-1";
					GTM_ID="GTM-L86JS";
					GTMUA_ID="UA-41638093-5";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-2";
					GTM_ID="GTM-4PBX9";
					GTMUA_ID="UA-41638093-5";
				}
			}
			System.out.println("Testing "+myURL+" for NZ links and Tracking ID: "+tracking_id);
		}
		if (myURL.contains(".co.nz"))
		{
			System.out.println("Testing "+myURL+" for AU links");
			if (myURL.contains("holden"))
			{
				tracking_id="UA-5003676-9";
				GTM_ID="GTM-FB9K";
				GTMUA_ID="UA-42561492-3";
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-5003676-10";
					GTM_ID="GTM-JPHVT";
					GTMUA_ID="UA-42561492-3";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-17";
					GTM_ID="GTM-L86JS";
					GTMUA_ID="UA-7653497-21";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-19";
					GTM_ID="GTM-4PBX9";
					GTMUA_ID="UA-7653497-21";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-3";
					GTM_ID="GTM-L86JS";
					GTMUA_ID="UA-41638093-6";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-4";
					GTM_ID="GTM-4PBX9";
					GTMUA_ID="UA-41638093-6";
				}
			}
			System.out.println("Testing "+myURL+" for AU links and Tracking ID: "+tracking_id);
			
		}	
		
		System.out.println("Current Environment has Google Tag Manager Container ID "+GTM_ID+" and Universal Analytics ID "+GTMUA_ID+" specified.");

		System.out.println(" ");

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
			pagedata= driver.findElement(By.cssSelector(" body")).getText();
			WriteToFile.myWriteAppend(PageSnapShot,pagedata);

			// Analysing disclaimer text for cross country references
			try
			{
				List <WebElement> popups = driver.findElements(By.cssSelector("a.disclaimer-pop-text"));
				WriteToFile.myWriteAppend(PageSnapShot,"All Disclaimer Text Below");
				disclaimercount =0;
				for (WebElement data : popups)
				{

					String disclaimerText= data.getAttribute("data-content");	
					WriteToFile.myWriteAppend(PageSnapShot," ");
					WriteToFile.myWriteAppend(PageSnapShot,"Disclaimer #"+(disclaimercount+1)+" :");
					WriteToFile.myWriteAppend(PageSnapShot,disclaimerText);
					if ((flag==1)&&(disclaimerText.contains("New Zealand") || disclaimerText.contains("co.nz")|| disclaimerText.contains("NewZealand") || disclaimerText.contains("nz")))
					{
						System.out.println("***** Disclaimer Text incorrect: Australian Site with NZ content*****");
						reportContent=reportContent+"<Font color =\"red\">Disclaimer #"+(disclaimercount+1)+" Text incorrect: Australian Site with NZ content</font><br>";
						WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect url: "+AllURLs.get(i)+" and is " +disclaimerText+" *****" );
						WriteToFile.myWriteAppend(console,"");

					}

					if ((flag==0)&&(disclaimerText.contains("Australia") || disclaimerText.contains("com.au")|| disclaimerText.contains("Australian") || disclaimerText.contains("au")))
					{
						System.out.println("*****Disclaimer Text incorrect: New Zealand site with AU content*****");
						reportContent=reportContent+"<Font color =\"red\">Disclaimer #"+(disclaimercount+1)+" Text incorrect: New Zealand site with AU content</font><br>";
						WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect for url: "+AllURLs.get(i)+" and is "+disclaimerText+ " *****" );
						WriteToFile.myWriteAppend(console,"");
					}
					else
					{
						System.out.println("Disclaimer text Correct");
						reportContent=reportContent+"Disclaimer #"+(disclaimercount+1)+" text correct<br>";
						WriteToFile.myWriteAppend(console,"Disclaimer #"+(disclaimercount+1)+" text correct");
						WriteToFile.myWriteAppend(console,"");

					}
					disclaimercount =disclaimercount +1;
				}
			}
			catch (Exception E)
			{
				WriteToFile.myWriteAppend(PageSnapShot," ");
				WriteToFile.myWriteAppend(PageSnapShot,"No Disclaimers for this page");
				reportContent=reportContent+"No Disclaimers for this page<br>";
				System.out.println("No Disclaimer on this page");
			}
			if (disclaimercount==0)
			{
				WriteToFile.myWriteAppend(PageSnapShot," ");
				WriteToFile.myWriteAppend(PageSnapShot,"No Disclaimers for this page");
				reportContent=reportContent+"No Disclaimers for this page<br>";
				System.out.println("No Disclaimer on this page");

			}
			WriteToFile.myWriteEOF(PageSnapShot);


			//		Checking page title for cross country references

			if ((flag==1)&&(driver.getTitle().contains("New Zealand") || driver.getTitle().contains("co.nz")|| driver.getTitle().contains("NewZealand")|| driver.getTitle().contains("nz")))
			{
				System.out.println("*****Page title incorrect: Australian Site with NZ title*****");
				System.out.println("Page Title: "+driver.getTitle());
				reportContent=reportContent+"<Font color =\"red\">Page title incorrect: Australian Site with NZ title<br>"+driver.getTitle()+"</font><br>";
				WriteToFile.myWriteAppend(console,"***** Page title incorrect url: "+AllURLs.get(i)+" and is " +driver.getTitle()+" *****" );
				WriteToFile.myWriteAppend(console,"");

			}

			if ((flag==0)&&(driver.getTitle().contains("Australia") || driver.getTitle().contains("com.au")|| driver.getTitle().contains("Australian")|| driver.getTitle().contains("au")))
			{
				System.out.println("*****Page title incorrect: New Zealand site with AU title*****");
				System.out.println("Page Title: "+driver.getTitle());
				reportContent=reportContent+"<Font color =\"red\">Page title incorrect: New Zealand site with AU title <br>"+driver.getTitle()+"</font><br>";
				WriteToFile.myWriteAppend(console,"***** Page title incorrect for url: "+AllURLs.get(i)+" and is " +driver.getTitle()+" *****" );
				WriteToFile.myWriteAppend(console,"");
			}
			else
			{
				System.out.println("Page title correct and is " +driver.getTitle());
				reportContent=reportContent+"Page title correct and is :<br>"+driver.getTitle()+"<br>";
				WriteToFile.myWriteAppend(console,"Page title correct for url: "+AllURLs.get(i)+" and is " +driver.getTitle() );
				WriteToFile.myWriteAppend(console,"");

			}
			// Check Google Analytics Script Present

			try{
				System.out.println("Looking for GA Tracking on current page");
				WebElement gaTracking = driver.findElement(By.xpath("//script[@type=\"text/javascript\"][contains(text(), '"+tracking_id+"')]"));
				String scriptdata = gaTracking.getAttribute("textContent");
				System.out.println("Google Analytics script present on page: "+AllURLs.get(i) );
				//System.out.println(scriptdata);   // Script can be viewed on Console(Currently disabled)
				System.out.println("");
				reportContent=reportContent+"Google Analytics script present on page: ID is "+tracking_id+"<br>";
				WriteToFile.myWriteAppend(console,"GA Tracking Exists, Script data below");
				WriteToFile.myWriteAppend(console,scriptdata);
				WriteToFile.myWriteAppend(console,"");
				WriteToFile.myWriteAppend(console,"");
			}
			catch (Exception E)
			{				
				System.out.println("*****GA Tracking id "+tracking_id+" does not match on page " +AllURLs.get(i));
				reportContent=reportContent+"<Font color =\"red\">Google Analytics script not on page: ID dos not match "+tracking_id+"Check Error Log</font><br>";
				WriteToFile.myWriteAppend(console,"*****GA Tracking id "+tracking_id+" does not match********");
				WriteToFile.myWriteAppend(console,"");
				WriteToFile.myWriteAppend(console,"");
			}

			
			
			//Google Tag Manager //script[contains(.,'GTM-L86JS')] ////script[contains(.,'UA-7653497-20')] and contains "dataLayer"
			
			
			try{
				System.out.println("Looking for GTM Tracking Container on current page");
				WebElement gtmTracking = driver.findElement(By.xpath("//script[contains(.,'"+GTM_ID+"')]"));
				scriptdata = gtmTracking.getAttribute("textContent");
				System.out.println("GTM container OK");
				reportContent=reportContent+"<Font color =\"green\">Google Tag Manager Container "+GTM_ID+" found.</font><br>";
			}
			catch (Exception E)
			{
				System.out.println("*****GTM Container id "+GTM_ID+" does not match on page " +AllURLs.get(i));
				reportContent=reportContent+"<Font color =\"red\">Google Tag Manager Container "+GTM_ID+" not found.</font><br>";
				WriteToFile.myWriteAppend(console,"*****GTM Container ID "+GTM_ID+" does not match********");
				WriteToFile.myWriteAppend(console,"");
				WriteToFile.myWriteAppend(console,"");
			}
			
			
			
			try{
				System.out.println("Looking for GTM UA-ID on the current page");
				WebElement gtmUATracking = driver.findElement(By.xpath("//script[contains(.,'"+GTMUA_ID+"')]"));
				GTMUA_scriptdata = gtmUATracking.getAttribute("textContent");
				if(GTMUA_scriptdata.contains("dataLayer"))
				{	
					System.out.println("Google Tag Manager script present on page: "+AllURLs.get(i) );
					//System.out.println(scriptdata);   // Script can be viewed on Console(Currently disabled)
					System.out.println("");
					reportContent=reportContent+"<Font color =\"green\">Google Tag Manager Script ID "+GTMUA_ID+" found<br></font>";
					WriteToFile.myWriteAppend(console,"GTM Tracking Works, Script data below");
					WriteToFile.myWriteAppend(console,"Container Script");
					WriteToFile.myWriteAppend(console,scriptdata);
					WriteToFile.myWriteAppend(console,GTMUA_scriptdata);
					WriteToFile.myWriteAppend(console,"");
					WriteToFile.myWriteAppend(console,"");
				}
				
				if(!GTMUA_scriptdata.contains("dataLayer"))
				{
					System.out.println("Google Tag Manager Container present but no UA tags on page: "+AllURLs.get(i) );
					//System.out.println(scriptdata);   // Script can be viewed on Console(Currently disabled)
					System.out.println("");
					reportContent=reportContent+"<Font color =\"red\">Google Tag Manager Script ID "+GTMUA_ID+" not found<br></font>";
					WriteToFile.myWriteAppend(console,"No UA Script, Script data below");
					WriteToFile.myWriteAppend(console,scriptdata);
					WriteToFile.myWriteAppend(console,GTMUA_scriptdata);
					WriteToFile.myWriteAppend(console,"");
					WriteToFile.myWriteAppend(console,"");
				}
			}

			catch(Exception E)
			{
				System.out.println("***** GTM UA Script errors on page: " +AllURLs.get(i));
				reportContent=reportContent+"<Font color =\"red\">Google Tag Manager Script ID "+GTMUA_ID+" not found<br></font>";
				WriteToFile.myWriteAppend(console,"*****GTM UA_ID "+GTMUA_ID+" does not match********");
				WriteToFile.myWriteAppend(console,"");
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
						WriteToFile.myWriteAppend(allAllURLs,"Link# "+AllURLs.size()+" "+"reference" + browserurl);
					}


					// AU links on NZ sites get stored here
					if (flag==0)
					{
						if (!(nonAllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(".com.au")))
						{
							nonAllURLs.add(browserurl);
							WriteToFile.myWriteAppend(console,"Cross Country Link Found :");
							WriteToFile.myWriteAppend(console,"Link# "+nonAllURLs.size()+" "+browserurl);
							WriteToFile.myWriteAppend(console,"");
							ccflag=1;

						}

					}

					// NZ links on AU site get stored here
					if (flag==1)
					{
						if (!(nonAllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(".co.nz")))
						{
							nonAllURLs.add(browserurl);
							WriteToFile.myWriteAppend(console,"Cross Country Link Found :");
							WriteToFile.myWriteAppend(console,"Link# "+nonAllURLs.size()+" "+browserurl);
							WriteToFile.myWriteAppend(console,"");
							ccflag=1;
						}
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
			if (ccflag==1)
			{
				reportContent=reportContent+"<Font color =\"red\">Cross Country Link Found<br></font><br>";
			}
			else
			{
				reportContent=reportContent+"No Cross Country Link Found<br>";
			}

			ccflag=0;


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
			
			
			//Meta Tag Extraction SEO testing
			try
			{
				MetaSEO=driver.findElements(By.xpath("//meta[contains(@property,'og:')]")) ;
				if(MetaSEO.size()==0)
				{
					reportContent=reportContent+"<Font color =\"red\"><b>No SEO Data Found</b></font><br>";				
				}
				else
				{	
					reportContent=reportContent+"<b>SEO Data</b><br>";
					for( WebElement metadata : MetaSEO)
					{
						if((metadata.getAttribute("property")).replace("og:", "").contentEquals("image"))
						{
							reportContent=reportContent+(metadata.getAttribute("property")).replace("og:", "")+" has value : \n"+"<a href=\""+metadata.getAttribute("Content")+"\"><img width=\"90\" height=\"60\" src=\""+metadata.getAttribute("Content")+"\"><br>";
						}
						else
						{
							reportContent=reportContent+(metadata.getAttribute("property")).replace("og:", "")+" has value : \n"+ metadata.getAttribute("Content")+"<br>" ;
						}
					}
				}
			}
			catch(Exception E)
			{
				reportContent=reportContent+"<Font color =\"red\"><b>Error Extracting SEO Data</b></font><br>";				
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
