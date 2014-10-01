package com.isobar.common.tools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.isobar.projecttaskmanager.pojo.SocialMedia;

public class CommonUtils {
	public static List<String> getAllLinkUrl(WebDriver driver, String siteName) throws InterruptedException, IOException {
		String currentUrl = null;
		List<String> urls = new ArrayList<String>();
		
		//allProjectURLs = new ArrayList<String>();  
		List<WebElement> hrefList = null;
		Thread.sleep(500);
		// extracting all URLs on the initial page and iterating through them. not looking into deeplinks

		hrefList = driver.findElements(By.cssSelector(" a"));
		for (WebElement link : hrefList) 
		{
			try {
				Thread.sleep(100);
				currentUrl=link.getAttribute("href");
				
			} catch (Exception e) {
		
				e.printStackTrace();
			}
			
			
			//currentUrl is absolute URL and start with http://www.holden
			//System.out.println("current URL is " + currentUrl);
			//get all url we needs here && !(currentUrl.contentEquals(startURL+"/"))
			if (!(urls.contains("/intent/tweet")) && !(urls.contains(currentUrl)) && (currentUrl!=null) && currentUrl.contains(siteName)  && !(currentUrl.contains("#"))&& !(currentUrl.contains(".pdf")) )
			{
				urls.add(currentUrl);
				//System.out.println("current URL is " + currentUrl);
				//WriteToFile.myWriteAppend(allAllURLs,"Link# "+urls.size()+" "+currentUrl);
			}
		}		
		
		return urls;
			
			
	}
	
	private void getAllUrsForTheSite(WebDriver driver, String startURL) throws InterruptedException, IOException {
		
		Set<String> allProjectURLSets =  new LinkedHashSet<String>();
		List<String> totalURLs = new ArrayList<String>();
		List<String> allProjectURLs = new ArrayList<String>();
		List<SocialMedia> socialMedias = new ArrayList<SocialMedia>();
		
		driver.get(startURL);
		String siteName = "www.holden.com.au";
		List<String> urls = CommonUtils.getAllLinkUrl(driver, siteName); 
		allProjectURLSets.addAll(urls);

		System.out.println("================allProjectURLs.size()" + totalURLs.size());
		for (String url:allProjectURLSets ){
			System.out.println(url);
		}	
	
		System.out.println("================");

		try {
			for(String url: totalURLs){	
				System.out.println("url" + url);
				driver.get(url);
				List<String> currentUrls = CommonUtils.getAllLinkUrl(driver, siteName); 
				allProjectURLSets.addAll(currentUrls);
				System.out.println("allProjectURLs.size()" + allProjectURLSets.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("allProjectURLs.size()" + allProjectURLSets.size());
	
		System.out.println(allProjectURLs);
		for(String url: totalURLs){		
			System.out.println("start URL Which is ===" );
			System.out.println(url);
			System.out.println("======================" );
			
			SocialMedia socialMedia = new SocialMedia();
			socialMedia.setPageUrl(url);
			socialMedias.add(socialMedia);			
			
		}
		
	}

}
