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
public class SocialMediaResources {
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


	private void runingSelelnium() throws InterruptedException, IOException {
		//step1 get all the urls from the csv files
		CsvReader csvReader = new CsvReader();
		totalURLs = csvReader.getUrlLocation();
		
		//Step1, no need to run everytime, it get the urls and save to the csv files
		//getAllUrsForTheSite();
	
		//Test code  
		//new vehicle, old vehicle, gallery, range,exterior
		/*
		totalURLs.add("http://www.holden.com.au/cars/commodore");
		totalURLs.add("http://www.holden.com.au/cars/barina-spark");
		totalURLs.add("http://www.holden.com.au/gallery/historicvehicles/1940s");
		totalURLs.add("http://www.holden.com.au/cars/barina-spark/exterior");
		totalURLs.add("http://www.holden.com.au/cars/malibu");
		totalURLs.add("http://www.holden.com.au/cars/barina/range/cdx");
		totalURLs.add("http://www.holden.com.au/cars/malibu/safety");
		totalURLs.add("http://www.holden.com.au/about/innovation/mylink/malibu");
		totalURLs.add("http://www.holden.com.au/cars/malibu/gallery");
		totalURLs.add("http://www.holden.com.au/cars/commodore/sedan-range/evoke");
		totalURLs.add("http://www.holden.com.au/cars/captiva-5/exterior");
		totalURLs.add("http://www.holden.com.au/cars/colorado-7/exterior");
		totalURLs.add("http://www.holden.com.au/cars/ute/range/sv6");
		totalURLs.add("http://www.holden.com.au/about/sponsorships");
		totalURLs.add("http://www.holden.com.au/uncover-trax#uncover-more");
	    */
		
		//step2 get all the social media
		for(String url: totalURLs){		
			System.out.println("start URL Which is ===" );
			System.out.println(url + "======================" );

			try {
				if (url.startsWith("http")) {
					SocialMedia socialMedia = getSocialMediaFromURL(url);
					socialMedias.add(socialMedia);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private SocialMedia getSocialMediaFromURL(String url){
		
		SocialMedia socialMedia = new SocialMedia();
		socialMedia.setPageUrl(url);
		
		try {
			//open the URL
			driver.get(url);	
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			//for the old vehicle templates such as barina spark
			//if ( driver.findElement(By.cssSelector("div.socialMediaModule"))!=null && driver.findElement(By.cssSelector("div.socialMediaModule")).isDisplayed()){
				setTwitterAndSendToFriendForOldVehiclePages(socialMedia);
			//}
		} catch (Exception e) {
			//it is ok if there is nothing
			System.out.println("it is not the old vehicle pages" + socialMedia.getPageUrl());
		}
		
		//if the tweet is still empty
		if(StringUtils.isEmpty(socialMedia.getTweet_url())){
			setTwitterAndSendToFriendForNewVehicleTemplate(socialMedia);
		}
        
		setFaceBookAndGooglePlus(socialMedia);		
		
		return socialMedia;
	
	}


	private void setFaceBookAndGooglePlus(SocialMedia socialMedia) {
		try
		{
			metaData=driver.findElements(By.xpath("//meta[contains(@property,'og:')]")) ;
		
			if(metaData.size()==0)
			{
				//DO Nothing
			}else{	
				
				   String facebook_title = null;
				   String facebook_description = null;
				   String facebook_image = null;
				
				   String googlePlus_title = null;
				   String googlePlus_image = null;
		

				for( WebElement metadata : metaData)
				{
					if((metadata.getAttribute("property")).replace("og:", "").contentEquals("image"))
					{
						//if the og image is just http://ww.holden.com.au. we just ignore it
						if(!metadata.getAttribute("Content").equalsIgnoreCase("http://www.holden.com.au")){
							facebook_image = metadata.getAttribute("Content");
							googlePlus_image = metadata.getAttribute("Content");
						}
				   }
					else if((metadata.getAttribute("property")).replace("og:", "").contentEquals("title")){
						facebook_title = metadata.getAttribute("Content");
						googlePlus_title = metadata.getAttribute("Content");
						
						
					}else if((metadata.getAttribute("property")).replace("og:", "").contentEquals("description")){
						facebook_description = metadata.getAttribute("Content");
					
					}
			
					
				}
				
				socialMedia.setFacebook_title(facebook_title);
				socialMedia.setFacebook_image(facebook_image);
				socialMedia.setFacebook_description(facebook_description);
				
				socialMedia.setGooglePlus_image(googlePlus_image);
				socialMedia.setGooglePlus_title(googlePlus_title);
				
			}
			
		}catch(Exception ex)
		{
			System.out.println("error when processing FaceBookAndGooglePlus" + socialMedia.getPageUrl());
		
		}
	}
	private void setTwitterAndSendToFriendForNewVehicleTemplate(SocialMedia socialMedia) {
		List<WebElement> twitters;
		WebElement sendToFriend;
		try {
			
			twitters =driver.findElements(By.cssSelector("div.m-social-small div.container-wrapper div div.text-center p a"));
			//System.out.println("twitters1 " + twitters);
			if(twitters== null || twitters.size()<1){
				//for gallery pages
				twitters = driver.findElements(By.cssSelector("li.social ul.unstyled li.twitter a.twitter"));
				//System.out.println("twitter is " + twitters);
				
			}
			//still can not find it? try this
			if(twitters== null || twitters.size()<1){
				twitters =driver.findElements(By.cssSelector("div.m-social-small div.text-center p a"));
				//System.out.println("twitters2 " + twitters);
			}
			
			
			if(twitters== null || twitters.size()<1){
				twitters =driver.findElements(By.cssSelector("div.small div.container-wrapper div div.text-center p a"));
				//System.out.println("twitters3 " + twitters);
			}
			
			if(twitters== null || twitters.size()<1){
				twitters =driver.findElements(By.cssSelector("a.share > div.name > div:nth-child(2)"));
				//System.out.println("twitters3 " + twitters);
			}
				

			for (WebElement we:twitters){
				String url =we.getAttribute("href");
			
			    if(url.contains("/intent/tweet")){
			    	String filterMessage = url.replaceAll("%27", "'").replaceAll("%21", "!").replaceAll("\\+", " ").replaceAll("%2C", ",").replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("&amp;", "&").replaceAll("%23", "#").replaceAll("%3F", "?").replace("%20", " ");
					String[] filterMessages  = filterMessage.split("&");
					String tweetHashTag = null;
					String tweetUrl = null;
					String tweetText = null;
					for(int i=0; i <filterMessages.length;i++){
						if (filterMessages[i].startsWith("url")){
							tweetUrl = filterMessages[i].replace("url=","").replaceAll("%26", "&");
								//System.out.println(tweetUrl);
						}else if (filterMessages[i].contains("hashtags")){
							 tweetHashTag = filterMessages[i].replace("hashtags=","").replace("via=", "").replace("original=", "").replaceAll("%26", "&");
								//System.out.println(tweetHashTag);
						}else if (filterMessages[i].contains("text")){
							tweetText = filterMessages[i].replace("text=","").replace("http://twitter.com/intent/tweet?", "").replaceAll("%26", "&");
						
						}
						//System.out.println(filterMessages[i]+"=======");
					}
					socialMedia.setTweet_hashTag(tweetHashTag);
					socialMedia.setTweet_message(tweetText);
					socialMedia.setTweet_url(tweetUrl);		
					break;
			    }	
			} 
		} catch (Exception e) {
			System.out.println("No twitter For New Vehicle Template " + socialMedia.getPageUrl());
		}

		
		if (StringUtils.isNotEmpty(socialMedia.getTweet_message())){
			try {
				if(driver.findElements(By.cssSelector("a.email"))!=null && driver.findElements(By.cssSelector("a.email")).size()>0){
					driver.findElements(By.cssSelector("a.email")).get(0).click();
				}
				
				
				
				Thread.sleep(500);
				
			    sendToFriend= driver.findElement(By.cssSelector("#sendToFriendForm.formContainer textarea"));
				//System.out.println("sendToFriend getText " + sendToFriend.getText());
				socialMedia.setSendToFriend_message(sendToFriend.getText());
	
			} catch (Exception e) {
				System.out.println("No SendToFriend1 For New Vehicle Template " + socialMedia.getPageUrl());
			}
		}
		
	}
	

	private void setTwitterAndSendToFriendForOldVehiclePages(SocialMedia socialMedia) {
		WebElement twitter = null;
		WebElement sendToFriend;
		try {
			//driver.findElement(By.xpath("/html/body/div[6]/div[3]/div/div/div/p[2]/a[2]"));
			try {
				twitter =driver.findElement(By.cssSelector("#twitter-widget-0.twitter-share-button"));
			} catch (Exception e) {
				//e.printStackTrace();
				System.out.println("can not find the socialMedia twitter for the url " + socialMedia.getPageUrl());
				
			}
			
		    
			if(twitter!=null){
				String input = twitter.getAttribute("src");
				String filterMessage = input.replaceAll("%27", "'").replaceAll("%21", "!").replaceAll("\\+", " ").replaceAll("%2C", ",").replaceAll("%3A", ":").replaceAll("%2F", "/").replaceAll("&amp;", "&").replaceAll("%23", "#").replaceAll("%3F", "?").replace("%20", " ");
				String[] filterMessages  = filterMessage.split("&");
				String tweetHashTag = null;
				String tweetUrl = null;
				String tweetText = null;
				for(int i=0; i <filterMessages.length;i++){
					if (filterMessages[i].startsWith("url")){
						tweetUrl = filterMessages[i].replace("url=","").replaceAll("%26", "&");
							//System.out.println(tweetUrl);
					}else if (filterMessages[i].contains("hashtags")){
						 tweetHashTag = filterMessages[i].replace("hashtags=","").replace("via=", "").replace("original=", "").replaceAll("%26", "&");
							//System.out.println(tweetHashTag);
					}else if (filterMessages[i].contains("text")){
						tweetText = filterMessages[i].replace("text=","").replaceAll("%26", "&");
					
					}
					//System.out.println(filterMessages[i]+"=======");
				}
				socialMedia.setTweet_hashTag(tweetHashTag);
				socialMedia.setTweet_message(tweetText);
				socialMedia.setTweet_url(tweetUrl);
				
			}

			
			if (StringUtils.isNotEmpty(socialMedia.getTweet_message())){
				
				try {
				    driver.findElements(By.xpath("//*[@id=\"sendToFriend\"]")).get(0).click();
				    
					Thread.sleep(500);
				    
					sendToFriend= driver.findElement(By.cssSelector("#sendToFriendForm.formContainer textarea"));
					socialMedia.setSendToFriend_message(sendToFriend.getText());
					
				} catch (Exception e) {
				
					System.out.println("No send to friend for old vehicle pages 1 " + socialMedia.getPageUrl());
				}
			}
			
			if (StringUtils.isEmpty(socialMedia.getSendToFriend_message())){
		
				try {					
				    driver.findElements(By.cssSelector("a.email")).get(0).click();
					Thread.sleep(500);
				    
					sendToFriend= driver.findElement(By.cssSelector("#sendToFriendForm.formContainer textarea"));
					socialMedia.setSendToFriend_message(sendToFriend.getText());
					
				} catch (Exception e) {
				
					//e.printStackTrace();
					System.out.println("url has the no send to friend 2" + socialMedia.getPageUrl());
					
				}
				
			}
			
			if (StringUtils.isEmpty(socialMedia.getSendToFriend_message())){
				
				try {					
				    driver.findElement(By.cssSelector("div#share.text-center p a.email")).click();
					Thread.sleep(500);
				    
					sendToFriend= driver.findElement(By.cssSelector("#sendToFriendForm.formContainer textarea"));
					socialMedia.setSendToFriend_message(sendToFriend.getText());
					
				} catch (Exception e) {
				
					//e.printStackTrace();
					System.out.println("url has the no send to friend 3" + socialMedia.getPageUrl());
					
				}
				
			}
			

		} catch (Exception e) {
			
		    System.out.println("NO TwitterAndSendToFriend" + socialMedia.getPageUrl());
			e.printStackTrace();
			//if there is no resources for the project, it is ok 
		
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
