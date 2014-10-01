package myToolsTestNG;
/**
 * 
 * ScreenshotPageCrawlerForms.java
 * Purpose:	To perform a check for the new vehicle to be present on forms and dropdowns.
 * 					** Check for the nominated vehicles on the build your car page
 * 					** Send forms to a nominated email address
 * 					** Check and store bluetooth compatibility screenshots 
 * 					** Check Forms page for Cross Country linkage ie .au links on .nz page and vice versa.
 * 					** Check Forms Page title for each page for Cross Country linkage.
 * 					** Grab Forms Body text and save in .doc files for each URL visited (MS Word's native Spell checking utility).
 * 					** Grab Disclaimer text and save in .doc files for each URL visited (MS Word's native Spell checking utility).
 * 					** Check Disclaimer text for each page for Cross Country linkage.
 * 					
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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
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

import org.apache.bcel.classfile.Method;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.mustache.Model;
//import org.openqa.selenium.support.ui.Select;

import com.google.common.base.Stopwatch;
//import org.openqa.selenium.firefox.FirefoxDriver;

import myMethods.*;

@SuppressWarnings("unused")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ScreenshotForms_AU {
	// Customise for each website
	//**********************************************************************************
	private static String myVehicle="Commodore";
	static String path="C:\\Output\\Screenshots\\";
	private static String myEnv="prod1"; // preprod//uat//www//dr//prod1//prod2//prodN
	static String tracking_id ="UA-XXXXXXXX-X"; // customise for each site
	static String email="gsharma@visualjazz.com.au";
	//**********************************************************************************

	// no customisation below this point.

	private static String myURL="";
	
	private static String count;
	static String Models="";
	String FormData="";
	static String CurrentPath;
	static String UniqueURL_List =CurrentPath+"Logs\\FoundURLs.txt";
	static String errorlog =CurrentPath+"Error\\ErrorURL_List.txt";
	static String ScreenshotResult;
	static WebDriver driver;
	String Name ;
	String Screenshot;
	String Screenshot2;
	String Vehiclevalue;
	final static Stopwatch stopwatch = new Stopwatch().start();
	final static Stopwatch stopwatch1 = new Stopwatch().start();
	String Content;
	String colour;
	String title ;
	File myFile ;
	File myFile1 ;
	int URLCount=0;
	int ccflag=0;
	String testname="Testing:";
	// Declared Variables 
	static String allAllURLs;
	static String console;
	static String allurls;
	static String pagedata;
	static String BluetoothResult;
	String browserurl;
	int disclaimercount;
	int previoustotalcount=0;
	int previousnativeurlCount=0;
	int previousnonNativeurlcount=0;
	static List<String> AllURLs = new ArrayList<String>();
	List<String> totalURLs = new ArrayList<String>();
	String reportContent=""; 
	List<String> nonAllURLs = new ArrayList<String>();
	List<String> AllModels= new ArrayList<String>();
	List<WebElement> AllModelsElements ;
	List<WebElement> BlutoothModels;
	List<WebElement> BlutoothModelsubs;
	List<WebElement> Blutoothphones;




	@BeforeSuite
	public static void setUp() throws Exception {
		CurrentPath=SetUp.createDateFolder(path);
		errorlog =CurrentPath+"Error\\ErrorURL_List.txt";
		ScreenshotResult=CurrentPath+"Screenshot.html";
		BluetoothResult=CurrentPath+"Bluetooth.html";
		System.out.println("Setting up the browsers");
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		WriteToFile.SOHTMLGENERAL(ScreenshotResult,"Forms for new vehicle: "+myVehicle);
		//WriteToFile.SOHTMLGENERAL(BluetoothResult,"Forms for new vehicle: "+myVehicle);
		WriteToFile.myWriteSOF(errorlog);
		// Directory Setup as per path variable.
		allAllURLs =CurrentPath+"Logs\\SubUrls.txt";
		console =CurrentPath+"Logs\\Console_log.txt";
		allurls =CurrentPath+"Logs\\AllURLs.txt";
		// Setting up Files.
		WriteToFile.myWriteSOF(allAllURLs);
		WriteToFile.myWriteSOF(console);
		WriteToFile.myWriteSOF(allurls);
		
		// Forms under Test
		/*
		AllURLs.add("http://preprod.holden.com.au/buildprice/vehicles"); // Done
		AllURLs.add("http://preprod.holden.com.au/forms/view-brochures?Vehicle="+myVehicle);//
		AllURLs.add("http://preprod.holden.com.au/forms/request-a-test-drive?Vehicle="+myVehicle);//Done
		AllURLs.add("http://preprod.holden.com.au/forms/get-a-quote?Vehicle="+myVehicle);//Done
		AllURLs.add("http://preprod.holden.com.au/about/innovation/mylink/"+myVehicle);//Done
		AllURLs.add("http://preprod.holden.com.au/ownership/bluetooth/bluetooth-compatibility");//Done
		 */
	}


	//@Ignore 
	@Test
	public void A_Test_BuildPrice() throws Exception {
		myURL="http://"+myEnv+".holden.com.au/buildprice/vehicles";
		driver.get(myURL);
		//testname= name.getMethodName();
		System.out.println(testname);
		
		WriteToFile.myWriteAppend(errorlog,"Scanning for 404,500, Redirection and network errors on Forms for new vehicle: "+myURL); // functionality in CheckTitle.CheckErrorsComplete Method
		int flag =0;
		if (myURL.contains(".com.au"))
		{
			flag =1;

			if(myURL.contains("holden"))
			{	

				tracking_id="UA-5073957-46";
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-8798612-16";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-1";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-16";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-1";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-2";
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
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-5003676-10";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-17";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-19";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-3";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-4";
				}
			}
			System.out.println("Testing "+myURL+" for AU links and Tracking ID: "+tracking_id);
		}	

		System.out.println(" ");


		totalURLs.add(myURL);
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+myURL);

		Thread.sleep(200);
		System.out.println("Analysing Contents of page "+myURL+"(No deep links)");
		WriteToFile.myWriteAppend(allAllURLs,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(console,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allurls,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allAllURLs," ");
		WriteToFile.myWriteAppend(console," ");
		WriteToFile.myWriteAppend(allurls," ");

		// Page text grab 
		String PageSnapShot="Logs\\WebPage_Test_BuildPrice.doc";
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
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect url: "+myURL+" and is " +disclaimerText+" *****" );
					WriteToFile.myWriteAppend(console,"");

				}

				if ((flag==0)&&(disclaimerText.contains("Australia") || disclaimerText.contains("com.au")|| disclaimerText.contains("Australian") || disclaimerText.contains("/au")))
				{
					System.out.println("*****Disclaimer Text incorrect: New Zealand site with AU content*****");
					reportContent=reportContent+"<Font color =\"red\">Disclaimer #"+(disclaimercount+1)+" Text incorrect: New Zealand site with AU content</font><br>";
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect for url: "+myURL+" and is "+disclaimerText+ " *****" );
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
			WriteToFile.myWriteAppend(console,"***** Page title incorrect url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");

		}

		if ((flag==0)&&(driver.getTitle().contains("Australia") || driver.getTitle().contains("com.au")|| driver.getTitle().contains("Australian")|| driver.getTitle().contains("au")))
		{
			System.out.println("*****Page title incorrect: New Zealand site with AU title*****");
			System.out.println("Page Title: "+driver.getTitle());
			reportContent=reportContent+"<Font color =\"red\">Page title incorrect: New Zealand site with AU title <br>"+driver.getTitle()+"</font><br>";
			WriteToFile.myWriteAppend(console,"***** Page title incorrect for url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");
		}
		else
		{
			System.out.println("Page title correct and is " +driver.getTitle());
			reportContent=reportContent+"Page title correct and is :<br>"+driver.getTitle()+"<br>";
			WriteToFile.myWriteAppend(console,"Page title correct for url: "+myURL+" and is " +driver.getTitle() );
			WriteToFile.myWriteAppend(console,"");

		}
		// Check Google Analytics Script Present

		try{
			System.out.println("Looking for GA Tracking on current page");
			WebElement gaTracking = driver.findElement(By.xpath("//script[@type=\"text/javascript\"][contains(text(), '"+tracking_id+"')]"));
			String scriptdata = gaTracking.getAttribute("textContent");
			System.out.println("Google Analytics script present on page: "+myURL );
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
			System.out.println("*****GA Tracking id "+tracking_id+" does not match on page " +myURL);
			reportContent=reportContent+"<Font color =\"red\">Google Analytics script not on page: ID does not match "+tracking_id+"Check Error Log</font><br>";
			WriteToFile.myWriteAppend(console,"*****GA Tracking id "+tracking_id+" does not match********");
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
				if (!(AllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(myURL)) && !(browserurl.contentEquals(myURL+"/")) && !(browserurl.contains("#"))&& !(browserurl.contains(".pdf")) )
				{

					AllURLs.add(browserurl);
					WriteToFile.myWriteAppend(allAllURLs,"Link# "+AllURLs.size()+" "+browserurl);
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
			reportContent=reportContent+"Cross Country Link Found<br>";
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
		Name="Page1";

		count="1";
		colour="D1D0CE";



		try{
			if(driver.findElement(By.xpath("//*[@id=\""+myVehicle+"\"]")).isDisplayed())
			{
				reportContent=reportContent+"<b>Vehicle "+myVehicle+" Visible on Grid</b><br>";
				System.out.println("Vehicle "+myVehicle+" Visible on Grid");
			}
		}
		catch (Exception E)

		{
			{
				reportContent=reportContent+"<Font color =\"red\"><b>Vehicle "+myVehicle+" Not Visible on Grid</b></font><br>";
				System.out.println("Vehicle "+myVehicle+" Not Visible on Grid");
			}
		}

		try {
			System.out.println("Trying");
			myFile= MyScreenshotTaker.Screenshot(driver);
			Screenshot=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile);
			Screenshot=Screenshot.replace(CurrentPath, "");
			driver.findElement(By.xpath("//*[@id=\"vehicleLink-"+myVehicle+"\"]")).click();
			System.out.println("Exploring range ");
			while(!(driver.findElement(By.xpath("//div[6]/div/div[2]/div/div/a/img")).isDisplayed()))
			{
			System.out.println("Waiting...");
			Thread.sleep(2000);
			}
			AllModelsElements= driver.findElements(By.xpath("//div[3]/div/div/div/div/a"));
			System.out.println("Models Found are : ");
			for (WebElement mod: AllModelsElements )
			{
				String finalAdd=mod.getAttribute("id").replace("modelLink-","");
				System.out.println(finalAdd);
				AllModels.add(finalAdd);
			}
			
			int mcount=0;
			for(String list: AllModels)
			{
				mcount=mcount+1;
				Models=Models+""+list+",";
				if(mcount==5)
				{
					Models=Models+"<br>";	
					mcount=0;
				}
			}
			myURL=driver.getCurrentUrl();
			Name="Page2";
			myFile1= MyScreenshotTaker.Screenshot(driver);
			Screenshot2=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile1);
			Screenshot2=Screenshot2.replace(CurrentPath, "");
			Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td>"+reportContent+"</td><td>Models Found:<br>"+Models+"<br><a href=\""+Screenshot2+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot2+"\"></a></td></tr>";
			WriteToFile.myWriteAppend(ScreenshotResult,Content);
		}
		catch (Exception E)
		{	Content="<tr><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href= \"Error/ErrorURL_List.txt\">No Screenshot</td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">Text Grab</a></td></tr>"; 
		WriteToFile.myWriteAppend(ScreenshotResult,Content);
		System.out.println("Unable to take a screenshot");
		}





		System.out.println("Crawler now has: "+AllURLs.size()+" Urls");
		reportContent="";
		System.out.println("");
		System.out.println("");
		

	}
	//@Ignore 
	@Test
	public void B_Test_RequestTestDrive() throws Exception {
		myURL="http://"+myEnv+".holden.com.au/forms/request-a-test-drive?Vehicle="+myVehicle;
		driver.get(myURL);//testname= name.getMethodName();
		System.out.println(testname);
		WriteToFile.myWriteAppend(errorlog,"Scanning for 404,500, Redirection and network errors on Forms for new vehicle: "+myURL); // functionality in CheckTitle.CheckErrorsComplete Method
		int flag =0;
		if (myURL.contains(".com.au"))
		{
			flag =1;

			if(myURL.contains("holden"))
			{	

				tracking_id="UA-5073957-46";
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-8798612-16";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-1";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-16";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-1";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-2";
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
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-5003676-10";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-17";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-19";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-3";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-4";
				}
			}
			System.out.println("Testing "+myURL+" for AU links and Tracking ID: "+tracking_id);
		}	

		System.out.println(" ");


		totalURLs.add(myURL);
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+myURL);

		Thread.sleep(200);
		System.out.println("Analysing Contents of page "+myURL+"(No deep links)");
		WriteToFile.myWriteAppend(allAllURLs,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(console,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allurls,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allAllURLs," ");
		WriteToFile.myWriteAppend(console," ");
		WriteToFile.myWriteAppend(allurls," ");

		// Page text grab 
		String PageSnapShot="Logs\\WebPage_RequestTestDrive.doc";
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
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect url: "+myURL+" and is " +disclaimerText+" *****" );
					WriteToFile.myWriteAppend(console,"");

				}

				if ((flag==0)&&(disclaimerText.contains("Australia") || disclaimerText.contains("com.au")|| disclaimerText.contains("Australian") || disclaimerText.contains("/au")))
				{
					System.out.println("*****Disclaimer Text incorrect: New Zealand site with AU content*****");
					reportContent=reportContent+"<Font color =\"red\">Disclaimer #"+(disclaimercount+1)+" Text incorrect: New Zealand site with AU content</font><br>";
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect for url: "+myURL+" and is "+disclaimerText+ " *****" );
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
			WriteToFile.myWriteAppend(console,"***** Page title incorrect url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");

		}

		if ((flag==0)&&(driver.getTitle().contains("Australia") || driver.getTitle().contains("com.au")|| driver.getTitle().contains("Australian")|| driver.getTitle().contains("au")))
		{
			System.out.println("*****Page title incorrect: New Zealand site with AU title*****");
			System.out.println("Page Title: "+driver.getTitle());
			reportContent=reportContent+"<Font color =\"red\">Page title incorrect: New Zealand site with AU title <br>"+driver.getTitle()+"</font><br>";
			WriteToFile.myWriteAppend(console,"***** Page title incorrect for url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");
		}
		else
		{
			System.out.println("Page title correct and is " +driver.getTitle());
			reportContent=reportContent+"Page title correct and is :<br>"+driver.getTitle()+"<br>";
			WriteToFile.myWriteAppend(console,"Page title correct for url: "+myURL+" and is " +driver.getTitle() );
			WriteToFile.myWriteAppend(console,"");

		}
		// Check Google Analytics Script Present

		try{
			System.out.println("Looking for GA Tracking on current page");
			WebElement gaTracking = driver.findElement(By.xpath("//script[@type=\"text/javascript\"][contains(text(), '"+tracking_id+"')]"));
			String scriptdata = gaTracking.getAttribute("textContent");
			System.out.println("Google Analytics script present on page: "+myURL );
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
			System.out.println("*****GA Tracking id "+tracking_id+" does not match on page " +myURL);
			reportContent=reportContent+"<Font color =\"red\">Google Analytics script not on page: ID does not match "+tracking_id+"Check Error Log</font><br>";
			WriteToFile.myWriteAppend(console,"*****GA Tracking id "+tracking_id+" does not match********");
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
				if (!(AllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(myURL)) && !(browserurl.contentEquals(myURL+"/")) && !(browserurl.contains("#"))&& !(browserurl.contains(".pdf")) )
				{

					AllURLs.add(browserurl);
					WriteToFile.myWriteAppend(allAllURLs,"Link# "+AllURLs.size()+" "+browserurl);
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
			reportContent=reportContent+"Cross Country Link Found<br>";
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
		Name=myURL.replace(myURL,"MainURL");
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
		// Adding table rows per url found

		count="2";
		colour="B6B6B4";

		try {
			System.out.println("Trying");
			myFile= MyScreenshotTaker.Screenshot(driver);
			Screenshot=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile);
			Screenshot=Screenshot.replace(CurrentPath, "");
		}
		catch(Exception E)
		{	System.out.println("First Screenshot error");
			Content="<tr><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href= \"Error/ErrorURL_List.txt\">No Screenshot</td>";
		}
		try{
			Select option = new Select (driver.findElement(By.xpath("//div/div/select[contains( @name,'title')]")));
			option.selectByValue("Mr");
			FormData=FormData+"Title: Mr ";
			driver.findElement(By.xpath("//div[2]/div/input")).sendKeys("RequestTestDrive");
			FormData=FormData+"First Name: RequestTestDrive ";
			driver.findElement(By.xpath("//div[2]/div[3]/div/input")).sendKeys("AutomatedTest");
			FormData=FormData+"Last Name: AutomatedTest ";
			driver.findElement(By.xpath("//div[2]/div[4]/div/input")).sendKeys(email);
			FormData=FormData+"Email: "+email+" <br>";
			driver.findElement(By.xpath("//div[2]/div[5]/div/input")).sendKeys("0412000000");
			FormData=FormData+"Phone: 0412000000 ";
			Select option1 = new Select (driver.findElement(By.xpath("//div/div/select[contains( @name,'contactTime')]")));
			option1.selectByValue("Afternoon");
			FormData=FormData+"Preferred contact time of the day: Afternoon ";
			Select option2 = new Select (driver.findElement(By.xpath("//div/div/select[contains( @name,'contactMethod')]")));
			option2.selectByValue("Email");	
			FormData=FormData+"Preferred contact method: Email<br> ";
			driver.findElement(By.xpath("//form/div[2]/div[7]/div/input")).sendKeys("9999");
			FormData=FormData+"Postcode: 9999 ";
			Thread.sleep(5000);
			FormData=FormData+"Dealer Confrmed: "+driver.findElement(By.xpath("//form/div[3]/div/div[3]/ul/li[1]/label/h3")).getText()+" <br>";		
			driver.findElement(By.xpath("//form/div[4]/div/button")).click();
			Thread.sleep(5000);
			
			Name="Page2";
			myFile1= MyScreenshotTaker.Screenshot(driver);
			Screenshot2=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile1);
			Screenshot2=Screenshot2.replace(CurrentPath, "");
			Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td>"+reportContent+"</td><td>"+FormData+"Thank You Screen<br><a href=\""+Screenshot2+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot2+"\"></a></td></tr>";
			
		}
		catch (Exception E)
		{	
		Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">No Screenshot</a></td></tr>"; 
		System.out.println("Unable to take second screenshot");
		}
		WriteToFile.myWriteAppend(ScreenshotResult,Content);
		System.out.println("Crawler now has: "+AllURLs.size()+" Urls");
		reportContent="";
		FormData="";

		System.out.println("");
		System.out.println("");

	}

	//@Ignore 
	@Test
	public void C_Test_GetQuote() throws Exception {
		myURL="http://"+myEnv+".holden.com.au/forms/get-a-quote?Vehicle="+myVehicle ;
		driver.get(myURL);//testname= name.getMethodName();
		System.out.println(testname);
		WriteToFile.myWriteAppend(errorlog,"Scanning for 404,500, Redirection and network errors on Forms for new vehicle: "+myURL); // functionality in CheckTitle.CheckErrorsComplete Method
		int flag =0;
		if (myURL.contains(".com.au"))
		{
			flag =1;

			if(myURL.contains("holden"))
			{	

				tracking_id="UA-5073957-46";
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-8798612-16";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-1";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-16";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-1";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-2";
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
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-5003676-10";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-17";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-19";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-3";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-4";
				}
			}
			System.out.println("Testing "+myURL+" for AU links and Tracking ID: "+tracking_id);
		}	

		System.out.println(" ");


		totalURLs.add(myURL);
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+myURL);

		Thread.sleep(200);
		System.out.println("Analysing Contents of page "+myURL+"(No deep links)");
		WriteToFile.myWriteAppend(allAllURLs,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(console,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allurls,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allAllURLs," ");
		WriteToFile.myWriteAppend(console," ");
		WriteToFile.myWriteAppend(allurls," ");

		// Page text grab 
		String PageSnapShot="Logs\\WebPage_GetQuote.doc";
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
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect url: "+myURL+" and is " +disclaimerText+" *****" );
					WriteToFile.myWriteAppend(console,"");

				}

				if ((flag==0)&&(disclaimerText.contains("Australia") || disclaimerText.contains("com.au")|| disclaimerText.contains("Australian") || disclaimerText.contains("/au")))
				{
					System.out.println("*****Disclaimer Text incorrect: New Zealand site with AU content*****");
					reportContent=reportContent+"<Font color =\"red\">Disclaimer #"+(disclaimercount+1)+" Text incorrect: New Zealand site with AU content</font><br>";
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect for url: "+myURL+" and is "+disclaimerText+ " *****" );
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
			WriteToFile.myWriteAppend(console,"***** Page title incorrect url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");

		}

		if ((flag==0)&&(driver.getTitle().contains("Australia") || driver.getTitle().contains("com.au")|| driver.getTitle().contains("Australian")|| driver.getTitle().contains("au")))
		{
			System.out.println("*****Page title incorrect: New Zealand site with AU title*****");
			System.out.println("Page Title: "+driver.getTitle());
			reportContent=reportContent+"<Font color =\"red\">Page title incorrect: New Zealand site with AU title <br>"+driver.getTitle()+"</font><br>";
			WriteToFile.myWriteAppend(console,"***** Page title incorrect for url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");
		}
		else
		{
			System.out.println("Page title correct and is " +driver.getTitle());
			reportContent=reportContent+"Page title correct and is :<br>"+driver.getTitle()+"<br>";
			WriteToFile.myWriteAppend(console,"Page title correct for url: "+myURL+" and is " +driver.getTitle() );
			WriteToFile.myWriteAppend(console,"");

		}
		// Check Google Analytics Script Present

		try{
			System.out.println("Looking for GA Tracking on current page");
			WebElement gaTracking = driver.findElement(By.xpath("//script[@type=\"text/javascript\"][contains(text(), '"+tracking_id+"')]"));
			String scriptdata = gaTracking.getAttribute("textContent");
			System.out.println("Google Analytics script present on page: "+myURL );
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
			System.out.println("*****GA Tracking id "+tracking_id+" does not match on page " +myURL);
			reportContent=reportContent+"<Font color =\"red\">Google Analytics script not on page: ID does not match "+tracking_id+"Check Error Log</font><br>";
			WriteToFile.myWriteAppend(console,"*****GA Tracking id "+tracking_id+" does not match********");
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
				if (!(AllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(myURL)) && !(browserurl.contentEquals(myURL+"/")) && !(browserurl.contains("#"))&& !(browserurl.contains(".pdf")) )
				{

					AllURLs.add(browserurl);
					WriteToFile.myWriteAppend(allAllURLs,"Link# "+AllURLs.size()+" "+browserurl);
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
			reportContent=reportContent+"Cross Country Link Found<br>";
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
		Name=myURL.replace(myURL,"MainURL");
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
		// Adding table rows per url found

		count="3";
		colour="D1D0CE";

		//}
		try {
			System.out.println("Trying");
			myFile= MyScreenshotTaker.Screenshot(driver);
			Screenshot=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile);
			Screenshot=Screenshot.replace(CurrentPath, "");
			Select option = new Select (driver.findElement(By.xpath("//div/div/select[contains( @name,'title')]")));
			option.selectByValue("Mr");
			FormData=FormData+"Title: Mr ";
			driver.findElement(By.xpath("//div[2]/div/input")).sendKeys("GetaQuote");
			FormData=FormData+"First Name: GetaQuote";
			driver.findElement(By.xpath("//div[2]/div[3]/div/input")).sendKeys("AutomatedTest");
			FormData=FormData+"Last Name: AutomatedTest ";
			driver.findElement(By.xpath("//div[2]/div[4]/div/input")).sendKeys(email);
			FormData=FormData+"Email: "+email+" <br>";
			driver.findElement(By.xpath("//div[2]/div[5]/div/input")).sendKeys("0412000000");
			FormData=FormData+"Phone: 0412000000 ";
			Select option1 = new Select (driver.findElement(By.xpath("//div/div/select[contains( @name,'contactTime')]")));
			option1.selectByValue("Afternoon");
			FormData=FormData+"Preferred contact time of the day: Afternoon ";
			Select option2 = new Select (driver.findElement(By.xpath("//div/div/select[contains( @name,'contactMethod')]")));
			option2.selectByValue("Email");	
			FormData=FormData+"Preferred contact method: Email <br>";
			driver.findElement(By.xpath("//form/div[2]/div[7]/div/input")).sendKeys("9999");
			FormData=FormData+"Postcode: 9999 ";
			Thread.sleep(5000);
			FormData=FormData+"Dealer Confrmed: "+driver.findElement(By.xpath("//form/div[3]/div/div[3]/ul/li[1]/label/h3")).getText()+" <br>";		
			driver.findElement(By.xpath("//form/div[4]/div/button")).click();
			Thread.sleep(5000);
			myURL=driver.getCurrentUrl();
			Name="Page2";
			myFile1= MyScreenshotTaker.Screenshot(driver);
			Screenshot2=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile1);
			Screenshot2=Screenshot2.replace(CurrentPath, "");
			PageSnapShot=PageSnapShot.replace(CurrentPath, "");
			Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td>"+reportContent+"</td><td>"+FormData+"Thank You Screen<br><a href=\""+Screenshot2+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot2+"\"></a></td></tr>";
			WriteToFile.myWriteAppend(ScreenshotResult,Content);
		}
		catch (Exception E)
		{	Content="<tr><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href= \"Error/ErrorURL_List.txt\">No Screenshot</td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">Text Grab</a></td></tr>"; 
		WriteToFile.myWriteAppend(ScreenshotResult,Content);
		System.out.println("Unable to take a screenshot");
		}
		System.out.println("Crawler now has: "+AllURLs.size()+" Urls");
		reportContent="";
		FormData="";
		System.out.println("");
		System.out.println("");


	}

	//@Ignore 
	@Test
	public void D_Test_Mylink() throws Exception {
		myURL="http://"+myEnv+".holden.com.au/about/innovation/mylink/"+myVehicle;
		driver.get(myURL);//testname= name.getMethodName();
		System.out.println(testname);
		WriteToFile.myWriteAppend(errorlog,"Scanning for 404,500, Redirection and network errors on Forms for new vehicle: "+myURL); // functionality in CheckTitle.CheckErrorsComplete Method
		int flag =0;
		if (myURL.contains(".com.au"))
		{
			flag =1;

			if(myURL.contains("holden"))
			{	

				tracking_id="UA-5073957-46";
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-8798612-16";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-1";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-16";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-1";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-2";
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
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-5003676-10";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-17";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-19";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-3";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-4";
				}
			}
			System.out.println("Testing "+myURL+" for AU links and Tracking ID: "+tracking_id);
		}	

		System.out.println(" ");


		totalURLs.add(myURL);
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+myURL);

		Thread.sleep(200);
		System.out.println("Analysing Contents of page "+myURL+"(No deep links)");
		WriteToFile.myWriteAppend(allAllURLs,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(console,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allurls,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allAllURLs," ");
		WriteToFile.myWriteAppend(console," ");
		WriteToFile.myWriteAppend(allurls," ");

		// Page text grab 
		String PageSnapShot=CurrentPath+"Logs\\WebPage_Mylink.doc";
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
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect url: "+myURL+" and is " +disclaimerText+" *****" );
					WriteToFile.myWriteAppend(console,"");

				}

				if ((flag==0)&&(disclaimerText.contains("Australia") || disclaimerText.contains("com.au")|| disclaimerText.contains("Australian") || disclaimerText.contains("/au")))
				{
					System.out.println("*****Disclaimer Text incorrect: New Zealand site with AU content*****");
					reportContent=reportContent+"<Font color =\"red\">Disclaimer #"+(disclaimercount+1)+" Text incorrect: New Zealand site with AU content</font><br>";
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect for url: "+myURL+" and is "+disclaimerText+ " *****" );
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
			WriteToFile.myWriteAppend(console,"***** Page title incorrect url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");

		}

		if ((flag==0)&&(driver.getTitle().contains("Australia") || driver.getTitle().contains("com.au")|| driver.getTitle().contains("Australian")|| driver.getTitle().contains("au")))
		{
			System.out.println("*****Page title incorrect: New Zealand site with AU title*****");
			System.out.println("Page Title: "+driver.getTitle());
			reportContent=reportContent+"<Font color =\"red\">Page title incorrect: New Zealand site with AU title <br>"+driver.getTitle()+"</font><br>";
			WriteToFile.myWriteAppend(console,"***** Page title incorrect for url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");
		}
		else
		{
			System.out.println("Page title correct and is " +driver.getTitle());
			reportContent=reportContent+"Page title correct and is :<br>"+driver.getTitle()+"<br>";
			WriteToFile.myWriteAppend(console,"Page title correct for url: "+myURL+" and is " +driver.getTitle() );
			WriteToFile.myWriteAppend(console,"");

		}
		// Check Google Analytics Script Present

		try{
			System.out.println("Looking for GA Tracking on current page");
			WebElement gaTracking = driver.findElement(By.xpath("//script[@type=\"text/javascript\"][contains(text(), '"+tracking_id+"')]"));
			String scriptdata = gaTracking.getAttribute("textContent");
			System.out.println("Google Analytics script present on page: "+myURL );
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
			System.out.println("*****GA Tracking id "+tracking_id+" does not match on page " +myURL);
			reportContent=reportContent+"<Font color =\"red\">Google Analytics script not on page: ID does not match "+tracking_id+"Check Error Log</font><br>";
			WriteToFile.myWriteAppend(console,"*****GA Tracking id "+tracking_id+" does not match********");
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
				if (!(AllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(myURL)) && !(browserurl.contentEquals(myURL+"/")) && !(browserurl.contains("#"))&& !(browserurl.contains(".pdf")) )
				{

					AllURLs.add(browserurl);
					WriteToFile.myWriteAppend(allAllURLs,"Link# "+AllURLs.size()+" "+browserurl);
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
			reportContent=reportContent+"Cross Country Link Found<br>";
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
		Name=myURL.replace(myURL,"MainURL");
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
		// Adding table rows per url found

		count="4";

		colour="B6B6B4";

		try {
			myFile= MyScreenshotTaker.Screenshot(driver);
			Screenshot=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile);
			Screenshot=Screenshot.replace(CurrentPath, "");
			PageSnapShot=PageSnapShot.replace(CurrentPath, "");
			Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">Text Grab</a></td></tr>";
			WriteToFile.myWriteAppend(ScreenshotResult,Content);
		}
		catch (Exception E)
		{	Content="<tr><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href= \"Error/ErrorURL_List.txt\">No Screenshot</td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">Text Grab</a></td></tr>"; 
		WriteToFile.myWriteAppend(ScreenshotResult,Content);
		System.out.println("Unable to take a screenshot");
		}
		System.out.println("Crawler now has: "+AllURLs.size()+" Urls");
		reportContent="";
		System.out.println("");
		System.out.println("");


	}
	//@Ignore 
	@Test
	public void E_Test_Bluetooth() throws Exception {
		myURL="http://"+myEnv+".holden.com.au/ownership/bluetooth/bluetooth-compatibility";
		driver.get(myURL);//testname= name.getMethodName();
		System.out.println(testname);
		WriteToFile.myWriteAppend(errorlog,"Scanning for 404,500, Redirection and network errors on Forms for new vehicle: "+myURL); // functionality in CheckTitle.CheckErrorsComplete Method
		int flag =0;
		if (myURL.contains(".com.au"))
		{
			flag =1;

			if(myURL.contains("holden"))
			{	

				tracking_id="UA-5073957-46";
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-8798612-16";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-1";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-16";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-1";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-2";
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
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-5003676-10";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-17";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-19";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-3";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-4";
				}
			}
			System.out.println("Testing "+myURL+" for AU links and Tracking ID: "+tracking_id);
		}	

		System.out.println(" ");


		totalURLs.add(myURL);
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+myURL);

		Thread.sleep(200);
		System.out.println("Analysing Contents of page "+myURL+"(No deep links)");
		WriteToFile.myWriteAppend(allAllURLs,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(console,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allurls,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allAllURLs," ");
		WriteToFile.myWriteAppend(console," ");
		WriteToFile.myWriteAppend(allurls," ");

		// Page text grab 
		String PageSnapShot=CurrentPath+"Logs\\"+(myURL.replace(myURL,"WebPage_")).replace("/", "_").replace("?","_").replace("__","_")+".doc";
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
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect url: "+myURL+" and is " +disclaimerText+" *****" );
					WriteToFile.myWriteAppend(console,"");

				}

				if ((flag==0)&&(disclaimerText.contains("Australia") || disclaimerText.contains("com.au")|| disclaimerText.contains("Australian") || disclaimerText.contains("/au")))
				{
					System.out.println("*****Disclaimer Text incorrect: New Zealand site with AU content*****");
					reportContent=reportContent+"<Font color =\"red\">Disclaimer #"+(disclaimercount+1)+" Text incorrect: New Zealand site with AU content</font><br>";
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect for url: "+myURL+" and is "+disclaimerText+ " *****" );
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
			WriteToFile.myWriteAppend(console,"***** Page title incorrect url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");

		}

		if ((flag==0)&&(driver.getTitle().contains("Australia") || driver.getTitle().contains("com.au")|| driver.getTitle().contains("Australian")|| driver.getTitle().contains("au")))
		{
			System.out.println("*****Page title incorrect: New Zealand site with AU title*****");
			System.out.println("Page Title: "+driver.getTitle());
			reportContent=reportContent+"<Font color =\"red\">Page title incorrect: New Zealand site with AU title <br>"+driver.getTitle()+"</font><br>";
			WriteToFile.myWriteAppend(console,"***** Page title incorrect for url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");
		}
		else
		{
			System.out.println("Page title correct and is " +driver.getTitle());
			reportContent=reportContent+"Page title correct and is :<br>"+driver.getTitle()+"<br>";
			WriteToFile.myWriteAppend(console,"Page title correct for url: "+myURL+" and is " +driver.getTitle() );
			WriteToFile.myWriteAppend(console,"");

		}
		// Check Google Analytics Script Present

		try{
			System.out.println("Looking for GA Tracking on current page");
			WebElement gaTracking = driver.findElement(By.xpath("//script[@type=\"text/javascript\"][contains(text(), '"+tracking_id+"')]"));
			String scriptdata = gaTracking.getAttribute("textContent");
			System.out.println("Google Analytics script present on page: "+myURL );
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
			System.out.println("*****GA Tracking id "+tracking_id+" does not match on page " +myURL);
			reportContent=reportContent+"<Font color =\"red\">Google Analytics script not on page: ID does not match "+tracking_id+"Check Error Log</font><br>";
			WriteToFile.myWriteAppend(console,"*****GA Tracking id "+tracking_id+" does not match********");
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
				if (!(AllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(myURL)) && !(browserurl.contentEquals(myURL+"/")) && !(browserurl.contains("#"))&& !(browserurl.contains(".pdf")) )
				{

					AllURLs.add(browserurl);
					WriteToFile.myWriteAppend(allAllURLs,"Link# "+AllURLs.size()+" "+browserurl);
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
			reportContent=reportContent+"Cross Country Link Found<br>";
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
		Name="Page1";
		count="5";
		colour="D1D0CE";

		BlutoothModels=driver.findElements(By.xpath("//div/div[2]/ul/li/select/option"));
		for (WebElement models_bt : BlutoothModels )

		{
			//System.out.println(models_bt.getText());
			if (models_bt.getText().contentEquals(myVehicle))
			{
				Vehiclevalue=models_bt.getAttribute("value");
				System.out.println("Vehicle Value is: "+Vehiclevalue);
			}

		}
		try{
			Select option = new Select (driver.findElement(By.xpath("//*[@id=\"vehicleId\"]")));
			option.selectByValue(Vehiclevalue);
			System.out.println("Vehicle Selected");
			reportContent=reportContent+"<b>Vehicle "+myVehicle+" Present in Select Vehicle Drop Down </b><br>";
			try{
				while(driver.findElement(By.xpath("//li[contains(@class,'loading')]")).isDisplayed())
				{
					System.out.println("waiting");
					Thread.sleep(2000);
				}
			}
			catch(Exception E)
			{//doing nothing
			}
		
		System.out.println("About to create file");
		try{
		WriteToFile.SOHTMLHPWBT(BluetoothResult,""+myVehicle+"");
		}
		catch(Exception E)
		{
			System.out.println("Unable to create file");
		}
		System.out.println("Html file created");

		}

	catch (Exception E)

	{
		reportContent=reportContent+"<Font color =\"red\"><b>Vehicle "+myVehicle+" Not Present in Select Vehicle Drop Down</b></font><br>";
	}

		try 
		{
			//System.out.println("Trying");
			myFile= MyScreenshotTaker.Screenshot(driver);
			Screenshot=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile);
			Screenshot=Screenshot.replace(CurrentPath, "");
			BlutoothModelsubs= driver.findElements(By.xpath("//ul/li[2]/select/option"));
			//System.out.println("found subs");
			for(WebElement ModelSubs: BlutoothModelsubs)

			{
				//System.out.println(ModelSubs.getText());
				if(ModelSubs.getText().contains(myVehicle))
				{
					
					Vehiclevalue=ModelSubs.getAttribute("value");
					Select option2 = new Select (driver.findElement(By.xpath("//*[@id=\"modelSelect\"]")));
					option2.selectByValue(Vehiclevalue);
					Thread.sleep(5000);
					//System.out.println("Subcatagory selected: "+ModelSubs.getText());
					Blutoothphones=driver.findElements(By.xpath("//ul/li[3]/select/option"));
					//System.out.println("Bluetooth phones loaded has "+Blutoothphones.size()+" elements");
					int PhoneCount=0;
					for(WebElement Phone: Blutoothphones)
					{			
						//System.out.println("Last loop:  "+Phone.getText());
						if(PhoneCount<=(Blutoothphones.size()-2))
						{
							//System.out.println((Blutoothphones.size()-2));
							Select option3 = new Select (driver.findElement(By.xpath("//*[@id=\"manufacturerSelect\"]")));
							//System.out.println("option 3 to be selected "+PhoneCount+"" );
							option3.selectByValue(""+PhoneCount+"");
							while(!(driver.findElement(By.xpath("//*[@id=\"phoneTable\"]")).isDisplayed()))
							{
								System.out.println("Waiting...");
							Thread.sleep(2000);
							}
							String testonly= driver.findElement(By.xpath("//ul/li[3]/select/option[contains(@value,"+PhoneCount+")]")).getText();
							myURL=driver.getCurrentUrl();
							Name="Blutooth\\"+ModelSubs.getText()+"_"+testonly;
							myFile1= MyScreenshotTaker.Screenshot(driver);
							Screenshot2=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL,"",Name, myFile1);
							if ((PhoneCount % 2)==0)
							{
								colour="B6B6B4";
							}
							else
							{
								colour="D1D0CE";
							}						
							WriteToFile.myWriteAppend(BluetoothResult, "<tr BGCOLOR= "+colour+"><td>"+ModelSubs.getText()+"</td><td>"+testonly+"</td><td><a href=\""+Screenshot2+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot2+"\"></a></td></tr>");
							PhoneCount=PhoneCount+1;
						}
					}

				}

			}

			PageSnapShot=PageSnapShot.replace(CurrentPath, "");
			Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td>"+reportContent+"</td><td><a href=\""+BluetoothResult.replace(CurrentPath,"")+"\">View Compatibility Screenshots</a></td></tr>";
			WriteToFile.myWriteAppend(ScreenshotResult,Content);
		}
		catch (Exception E)
		{	Content="<tr><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href= \"Error/ErrorURL_List.txt\">No Screenshot</td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">Text Grab</a></td></tr>"; 
		WriteToFile.myWriteAppend(ScreenshotResult,Content);
		System.out.println("Unable to take a screenshot");
		}





		System.out.println("Crawler now has: "+AllURLs.size()+" Urls");
		reportContent="";

		System.out.println("");System.out.println("");

	}

	//@Ignore 
	@Test
	public void F_Test_ViewBrochures() throws Exception {
		myURL="http://"+myEnv+".holden.com.au/forms/view-brochures?Vehicle="+myVehicle;
		driver.get(myURL);//testname= name.getMethodName();
		System.out.println(testname);
		WriteToFile.myWriteAppend(errorlog,"Scanning for 404,500, Redirection and network errors on Forms for new vehicle: "+myURL); // functionality in CheckTitle.CheckErrorsComplete Method
		int flag =0;
		if (myURL.contains(".com.au"))
		{
			flag =1;

			if(myURL.contains("holden"))
			{	

				tracking_id="UA-5073957-46";
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-8798612-16";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-1";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-16";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-1";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-2";
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
				if(myURL.contains("m.holden"))
				{
					tracking_id="UA-5003676-10";
				}
				if(myURL.contains("preprod"))
				{
					tracking_id="UA-7653497-17";
				}
				if(myURL.contains("m.preprod"))
				{
					tracking_id="UA-7653497-19";
				}
				if(myURL.contains("uat"))
				{
					tracking_id="UA-41638093-3";
				}
				if(myURL.contains("m.uat"))
				{
					tracking_id="UA-41638093-4";
				}
			}
			System.out.println("Testing "+myURL+" for AU links and Tracking ID: "+tracking_id);
		}	

		System.out.println(" ");


		totalURLs.add(myURL);
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+myURL);

		Thread.sleep(200);
		System.out.println("Analysing Contents of page "+myURL+"(No deep links)");
		WriteToFile.myWriteAppend(allAllURLs,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(console,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allurls,"**Analysing Contents of page "+myURL);
		WriteToFile.myWriteAppend(allAllURLs," ");
		WriteToFile.myWriteAppend(console," ");
		WriteToFile.myWriteAppend(allurls," ");

		// Page text grab 
		String PageSnapShot=CurrentPath+"Logs\\"+(myURL.replace(myURL,"WebPage_")).replace("/", "_").replace("?","_").replace("__","_")+".doc";
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
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect url: "+myURL+" and is " +disclaimerText+" *****" );
					WriteToFile.myWriteAppend(console,"");

				}

				if ((flag==0)&&(disclaimerText.contains("Australia") || disclaimerText.contains("com.au")|| disclaimerText.contains("Australian") || disclaimerText.contains("/au")))
				{
					System.out.println("*****Disclaimer Text incorrect: New Zealand site with AU content*****");
					reportContent=reportContent+"<Font color =\"red\">Disclaimer #"+(disclaimercount+1)+" Text incorrect: New Zealand site with AU content</font><br>";
					WriteToFile.myWriteAppend(console,"***** Disclaimer #"+(disclaimercount+1)+" Text incorrect for url: "+myURL+" and is "+disclaimerText+ " *****" );
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
			WriteToFile.myWriteAppend(console,"***** Page title incorrect url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");

		}

		if ((flag==0)&&(driver.getTitle().contains("Australia") || driver.getTitle().contains("com.au")|| driver.getTitle().contains("Australian")|| driver.getTitle().contains("au")))
		{
			System.out.println("*****Page title incorrect: New Zealand site with AU title*****");
			System.out.println("Page Title: "+driver.getTitle());
			reportContent=reportContent+"<Font color =\"red\">Page title incorrect: New Zealand site with AU title <br>"+driver.getTitle()+"</font><br>";
			WriteToFile.myWriteAppend(console,"***** Page title incorrect for url: "+myURL+" and is " +driver.getTitle()+" *****" );
			WriteToFile.myWriteAppend(console,"");
		}
		else
		{
			System.out.println("Page title correct and is " +driver.getTitle());
			reportContent=reportContent+"Page title correct and is :<br>"+driver.getTitle()+"<br>";
			WriteToFile.myWriteAppend(console,"Page title correct for url: "+myURL+" and is " +driver.getTitle() );
			WriteToFile.myWriteAppend(console,"");

		}
		// Check Google Analytics Script Present

		try{
			System.out.println("Looking for GA Tracking on current page");
			WebElement gaTracking = driver.findElement(By.xpath("//script[@type=\"text/javascript\"][contains(text(), '"+tracking_id+"')]"));
			String scriptdata = gaTracking.getAttribute("textContent");
			System.out.println("Google Analytics script present on page: "+myURL );
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
			System.out.println("*****GA Tracking id "+tracking_id+" does not match on page " +myURL);
			reportContent=reportContent+"<Font color =\"red\">Google Analytics script not on page: ID does not match "+tracking_id+"Check Error Log</font><br>";
			WriteToFile.myWriteAppend(console,"*****GA Tracking id "+tracking_id+" does not match********");
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
				if (!(AllURLs.contains(browserurl)) && (browserurl!=null) && (browserurl.contains(myURL)) && !(browserurl.contentEquals(myURL+"/")) && !(browserurl.contains("#"))&& !(browserurl.contains(".pdf")) )
				{

					AllURLs.add(browserurl);
					WriteToFile.myWriteAppend(allAllURLs,"Link# "+AllURLs.size()+" "+browserurl);
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
			reportContent=reportContent+"Cross Country Link Found<br>";
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
		Name=myURL.replace(myURL,"MainURL");
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
		// Adding table rows per url found

		count="6";
		colour="B6B6B4";

		try {
			System.out.println("Trying");
			myFile= MyScreenshotTaker.Screenshot(driver);
			Screenshot=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL, count,Name, myFile);
			Screenshot=Screenshot.replace(CurrentPath, "");
			driver.findElement(By.xpath("//div/div[2]/form/div/ul/li[2]/a")).click();
			FormData=FormData+"Selected Brochure to be emailed<br> ";
			driver.findElement(By.xpath("//div[2]/div[2]/div/div/input")).sendKeys(email);
			FormData=FormData+"Email: "+email+" <br>";
			Thread.sleep(5000);
			driver.findElement(By.xpath("//form/div[3]/div/button")).click();
			Thread.sleep(5000);
			myURL=driver.getCurrentUrl();
			Name="Page2";
			myFile1= MyScreenshotTaker.Screenshot(driver);
			Screenshot2=CheckTitle.CheckErrorsComplete(driver, CurrentPath, myURL,count,Name, myFile1);
			Screenshot2=Screenshot2.replace(CurrentPath, "");
			PageSnapShot=PageSnapShot.replace(CurrentPath, "");
			Content="<tr BGCOLOR= "+colour+"><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href=\""+Screenshot+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot+"\"></a></td><td>"+reportContent+"</td><td>"+FormData+"Thank You Screen<br><a href=\""+Screenshot2+"\"><img width=\"120\" height=\"70\" src=\""+Screenshot2+"\"></a></td></tr>";
			WriteToFile.myWriteAppend(ScreenshotResult,Content);
		}
		catch (Exception E)
		{	Content="<tr><td>"+count+"</td><td><a href = \""+myURL+"\">"+testname+"<br>"+myURL+"</td></a><td><a href= \"Error/ErrorURL_List.txt\">No Screenshot</td><td>"+reportContent+"</td><td><a href=\""+PageSnapShot+"\">Text Grab</a></td></tr>"; 
		WriteToFile.myWriteAppend(ScreenshotResult,Content);
		System.out.println("Unable to take a screenshot");
		}
		System.out.println("Crawler now has: "+AllURLs.size()+" Urls");
		reportContent="";
		FormData="";

		System.out.println("");
		System.out.println("");
		
	}


	@AfterSuite

	public static void closeBrowser() throws Exception {

		//last layout with link to .txt files produced during execution
		WriteToFile.myWriteAppend(BluetoothResult,"</table>");
		WriteToFile.myWriteAppend(BluetoothResult,"</center>");
		WriteToFile.myWriteAppend(ScreenshotResult,"</table>");
		WriteToFile.myWriteAppend(ScreenshotResult,"</center>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<table border = \"0\" cellspacing = \"0\" cellpadding = \"10\"><tr>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Error/ErrorURL_List.txt\"> View Url Errors</a></td>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Logs/Console_log.txt\"> View Page Errors</a></td>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Logs/SubUrls.txt\"> Links Extracted Per Page</a></td>");
		WriteToFile.myWriteAppend(ScreenshotResult,"<td><a href= \"Logs/AllURLs.txt\"> All Extracted Links</td></tr></table>");
		long seconds=stopwatch.stop().elapsed(TimeUnit.SECONDS);
		WriteToFile.myWriteAppend(ScreenshotResult, " <br>");
		WriteToFile.myWriteAppend(ScreenshotResult, "<br>");
		WriteToFile.myWriteAppend(ScreenshotResult, "Time of execution in seconds: " +seconds);
		WriteToFile.myWriteEOF(errorlog);
		WriteToFile.myWriteAppend(console, "Time of execution in Seconds: " +seconds+" Seconds"); 
		WriteToFile.myWriteAppend(allurls, "Time of execution in Seconds: " +seconds+" Seconds"); 
		WriteToFile.myWriteEOF(console);
		WriteToFile.myWriteEOF(allurls);
		WriteToFile.EOHTMLGEN(BluetoothResult);
		WriteToFile.EOHTMLGEN(ScreenshotResult);
		File htmlFile = new File(ScreenshotResult);
		Desktop.getDesktop().browse(htmlFile.toURI());
		driver.quit();
		System.out.println("Shutting down the browser");
		System.out.println("Time of execution in Seconds: " +seconds );
	}

}
