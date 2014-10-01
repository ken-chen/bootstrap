package myToolsTestNG;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import myMethods.CheckTitle;
import myMethods.MyScreenshotTaker;
import myMethods.SetUp;
import myMethods.WriteToFile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.google.common.base.Stopwatch;

public class ScreenShotBuildYourCar {

	// Customise for each website
		//**********************************************************************************
		private static String myVehicle="Commodore";
		static String path="C:\\Output\\Screenshots\\";
		private static String myEnv="dev"; // preprod//uat//www//dr//prod1//prod2//prodN
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
}
