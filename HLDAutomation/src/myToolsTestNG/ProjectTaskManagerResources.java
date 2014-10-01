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
import java.util.List;
import java.util.concurrent.TimeUnit;

import myMethods.MyScreenshotTaker;
import myMethods.SetUp;
import myMethods.WriteToFile;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.base.Stopwatch;
import com.isobar.projecttaskmanager.utils.CutomFileUtils;

/**
 * @author kchen
 * 
 * This testNG is used to get all PM's 
 * Use Firefox plugin Firefinder to get the CSS Path or XPATH
 */
public class ProjectTaskManagerResources {
	// Customise for each website
	//**********************************************************************************
	private static String startURL="https://taskmanager.visualjazz.com.au/Dashboard.aspx";
	static String path="C:\\Output\\Screenshots\\HoldenProjectWork\\";
		//**********************************************************************************
	//Sam Chappell is not in the ProjectTaskManager
	
    //String[] allPms = { "Elise Plumbley", "Jessica Snell",  "Kara Bombell",  "Marcus Van Malsen", "Melissa Hocking",  "Michael Di Natale",  "Steven McGrath", "Tomma Morris", "Tony Day",  "Alex Curtain", "Anthony Mangos", "Bobby Hollingsworth", "Brianna Lacy", "Dan Collado", "Danielle Slim",  "Nick Hardie-Grant",  "Olivier Laude",  "Robert Dauth",  "Rose Harvey",  "Steph Webster",  "Taryn Inkster", "Tim Den Braber", "Tish Tambakau"}; 
	
	String[] allPms = {"Alex Curtain","Alex Borland","Alistair Ward","Andrew Yeoh","Ani Moller","Anika Magee","Anthony Mangos","Anthony Sonego","Asha Morgan-Kellow",
			"Bobby Hollingsworth","Bree Winchester","Brianna Lacy","Caroline Downes","Damian Guiney","Dan Collado","Danielle Slim","Dave Sharpe","Ebony Bell","Emma Grebezs","Erik Hallander","Gale Garcia","Grant Henderson","Hayley Rose","Jeanette Phang","Jessica Tham","Jessica Reeve","Jessica Snell","Jigesh Mody","Joan O'Reilly","Kara Jenkins","Kara Bombell",
			"Konrad Spilva","Kunal Bhatia","Kylie Anderson","Lee Kociski","Lorna Whitehead","Marcus Van Malsen","Marteen Burger","Martin Hong",
			"Michael Di Natale","Mitzi Polacsek","Nathan Kaso","Nicholas Miers","Nick Hardie-Grant","Olivier Laude","Paul Chappell",
			"Penny Cai","Rianne Schuwer","Robert Dauth","Rod Farmer","Rose Harvey","Roseli Diniz","Sam Chappell",
			"Samantha Wight","Simon Small","Simon Stocks","Steph Webster","Steven McGrath","Taryn Inkster","Thomas Tearle","Tim Den Braber","Tim Melville","Tish Tambakau","Tom Ashmor",
			"Tomma Morris","Tony Day","Verity Tuck","Victor Chieng","Victoria Gehrig","Xavier Verhoeven"
 };
	// no customisation below this point.
	private static String count;
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
	List<String> totalURLs = new ArrayList<String>();
	String reportContent=""; 
	List<String> nonAllURLs = new ArrayList<String>();

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
		
		//change the PM name and run it again
	    //projectManagerName = "Tony Day";

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
	
		for(String currentPM: allPms){
			projectManagerName = currentPM;
			driver.get(startURL);
			
	
			processManagerByName(projectManagerName);	
			System.out.println("projectManagerName " +projectManagerName + " find and Starting report");
			
			getManagerActiveProjects();
			
			List<String> imageList =processManagerActiveProjects();
			CutomFileUtils.generateExcelReport(projectManagerName, imageList);
			System.out.println("Project finished running for PM " + projectManagerName);
			
		}
	}




	/**
	 * @param count
	 * @param shortText 
	 * @throws IOException
	 */
	private String takeScreenShootIntoTheDiskDrive(String count, String shortText) throws IOException {
		myFile= MyScreenshotTaker.Screenshot(driver);
		
		screenshotResource= CutomFileUtils.getfileSavedLocation(CurrentPath, startURL, count+shortText, "Resources", myFile);
		System.out.println(screenshotResource);
		return screenshotResource;
	}


	/**
	 * @throws InterruptedException
	 * @throws IOException 
	 */
	private List<String> processManagerActiveProjects() throws InterruptedException, IOException {
		System.out.println("Al project for PM  size are " + allProjectURLs.size());
		List<String> urlLinks = new ArrayList<String>();
		for (int i=0; i < allProjectURLs.size();i++){
			driver.get(allProjectURLs.get(i));
			System.out.println(i + ". start process project url is " + allProjectURLs.get(i));
			//click the resources
			//if it is the js, we can use click, outher wise we would use dirver.get
			//we need to check if the resource existing
			WebElement we = null;
			
			try {
				 we = driver.findElement(By.xpath("//*[@id=\"PlaceHolderBody_hypResources\"]"));
				 String imageURL = takeScreenShootIntoTheDiskDrive(String.valueOf(i),"level1");
				 urlLinks.add(imageURL);
			} catch (Exception e) {
			    System.out.println("NO resources for the project");
				//e.printStackTrace();
				//if there is no resources for the project, it is ok 
				continue;
			}

			String resourceLink = we.getAttribute("href");
			driver.get(resourceLink);
			Thread.sleep(1000);
			
			//System.out.println("get to the destination page 12 weeks");
			driver.findElement(By.xpath("//*[@id=\"PlaceHolderBody_lnkWeek12\"]")).click();
			
			//need to wait page finish loading first
			while (driver.findElement(By.xpath("/html/body/form/div[3]/div[2]/span")).isDisplayed())
			{
				//System.out.println("Waiting Page to load ...");
				Thread.sleep(2000);
			}
			
			//Name of the person,click each of them , NOT Required for now
			List<WebElement> resources = driver.findElements(By.xpath("//div/div/img[contains(@src,'plus.gif')]"));
			
			for(WebElement resource: resources)
			{
				resource.click();
				Thread.sleep(1000);
			}
			Thread.sleep(2000);
			
		
			WebElement we2 =driver.findElement(By.xpath("/html/body/form/div[3]/div[3]/div/div[3]"));
			String value2 = we2.getText();
			System.out.println();
			String shortText = "No people found!";
			if(shortText.equalsIgnoreCase(value2)){
				shortText = "short";
			}else{
				shortText = "long";
			}
			String imageURL = takeScreenShootIntoTheDiskDrive(String.valueOf(i),shortText);
			urlLinks.add(imageURL);
			
		//	break;
			
		}
		return urlLinks;
		
	}


	/**
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private void getManagerActiveProjects() throws InterruptedException, IOException {
		
		allProjectURLs = new ArrayList<String>();  
		List<WebElement> hrefList = null;
		Thread.sleep(500);
		// extracting all URLs on the initial page and iterating through them. not looking into deeplinks

			hrefList = driver.findElements(By.cssSelector(" a"));
			for (WebElement link : hrefList) 
			{
				currentUrl=link.getAttribute("href");
				
				//currentUrl is absolute URL and start with http://www.holden
				//System.out.println("current URL is " + currentUrl);
				//get all url we needs here
				if (!(allProjectURLs.contains(currentUrl)) && (currentUrl!=null) && (currentUrl.contains("https://taskmanager.visualjazz.com.au/ProjectInfo.aspx?task=view&id")) && !(currentUrl.contentEquals(startURL+"/")) && !(currentUrl.contains("#"))&& !(currentUrl.contains(".pdf")) )
				{
					allProjectURLs.add(currentUrl);
					//System.out.println("current URL is " + currentUrl);
					WriteToFile.myWriteAppend(allAllURLs,"Link# "+allProjectURLs.size()+" "+currentUrl);
				}
			}
	
	}


	/**
	 * @param name
	 * @throws InterruptedException
	 */
	private void processManagerByName(String name) throws InterruptedException {
		Select select = new Select ( driver.findElement(By.cssSelector("html body form#TheForm div#wrapper div#FilterPanel div.left select#PlaceHolderBody_Employees_DropDown")));
		select.selectByVisibleText(name);
		Thread.sleep(1000);
	}


	/**
	 * @throws InterruptedException
	 * WHEN build this script at home, we need to login first
	 */
	private void loginFromHome() throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys("kchen");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("*******");
		driver.findElement(By.xpath("//*[@id=\"SubmitCreds\"]")).click();
		Thread.sleep(1000);
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
