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
 */

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import myMethods.SetUp;
import myMethods.WriteToFile;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Stopwatch;
import com.isobar.projecttaskmanager.pojo.SocialMedia;
import com.isobar.projecttaskmanager.utils.CutomFileUtils;

/**
 * @author kchen
 * 
 */
public class SimpleSeleniumTest {
	// Customise for each website
	//**********************************************************************************
	private static String startURL="http://www.holden.com.au";
	static String path="C:\\Output\\Screenshots\\HoldenProjectWork\\";
		//**********************************************************************************
	static String CurrentPath;
	static String UniqueURL_List =CurrentPath+"Logs\\FoundURLs.txt";
	static String ScreenshotResult;
	String screenshotResource;
	static WebDriver driver;
	static String projectManagerName;
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
	String currentUrl;
	int disclaimercount;
	int previoustotalcount=0;
	int previousnativeurlCount=0;
	int previousnonNativeurlcount=0;
	List<String> allProjectURLs = new ArrayList<String>();
	Set<String> allProjectURLSets =  new LinkedHashSet<String>();
	
	List<String> totalURLs = new ArrayList<String>();
	String reportContent=""; 
	List<String> nonAllURLs = new ArrayList<String>();
	List<WebElement> metaData = new ArrayList<WebElement>();
	
	List<SocialMedia> socialMedias = new ArrayList<SocialMedia>();

	@BeforeMethod
	public static void setUp() throws Exception {
		CurrentPath=SetUp.createDateFolder(path);
		ScreenshotResult=CurrentPath+"Screenshot.html";
		System.out.println("Setting up the browsers");
		driver = new FirefoxDriver();
		//driver = new ChromeDriver();
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
	public void test_Browser() throws Exception {
		
		System.out.println("Scripts start ");
		//Starting URL added to array to initialise page crawl
		WriteToFile.myWriteAppend(allurls," Starting Link# "+totalURLs.size()+" "+startURL);
	   
		
		//main function
		try {
	    	runingSelelnium();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		Thread.sleep(200);		
		//step3 save to the spreadsheet
		CutomFileUtils.generateExcelReportForSocial(socialMedias);
	}

	private void loginFromHome() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"ls_username\"]")).sendKeys("jwchen0208");
		driver.findElement(By.xpath("//*[@id=\"ls_password\"]")).sendKeys("19830208");
		driver.findElement(By.xpath("//*[@id=\"lsform\"]/div/div/table/tbody/tr[2]/td[3]/button")).click();
		Thread.sleep(1000);
		//aosteps
		//Password123
	}
	private void runingSelelnium() throws InterruptedException, IOException {
		//step1 get all the urls from the csv files
		totalURLs.add("http://www.oursteps.com.au/bbs/forum.php?mod=viewthread&tid=953027&extra=page%3D1&page=37");
		//step2 get all the social media
		for(String url: totalURLs){		
			System.out.println("start URL Which is ===" );
			System.out.println(url + "======================" );

			//*[@id="ls_username"]
			//*[@id="ls_password"]
			
			//*[@id="lsform"]/div/div/table/tbody/tr[2]/td[3]/button/em
			//*[@id="lsform"]/div/div/table/tbody/tr[2]/td[3]/button
			try {
				if (url.startsWith("http")) {
					try {
						//open the URL
						driver.get(url);	
						Thread.sleep(1000);
						loginFromHome();
						Thread.sleep(5000);

						for(int i =0; i<=200;i++){
							driver.findElement(By.xpath("//*[@id=\"fastpostmessage\"]")).sendKeys(":indian + "+ i);
							driver.findElement(By.xpath("//*[@id=\"fastpostsubmit\"]/strong")).click();
							Thread.sleep(20000);
						}
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}



	@AfterClass	

	public static void closeBrowser() throws Exception {
		long seconds=stopwatch.stop().elapsed(TimeUnit.SECONDS);
		WriteToFile.myWriteAppend(ScreenshotResult, "  ");
		WriteToFile.myWriteAppend(ScreenshotResult, " ");
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
