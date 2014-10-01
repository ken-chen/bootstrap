package com.isobar.selenium.holden.forms;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.isobar.projecttaskmanager.pojo.FormData;

public class FormsTest {

	private static WebDriver driver;
	private String formURL = "https://secure.holden.com.au/forms/get-a-quote";
	private String vehicle = "Barina";
	static String email = "jwchen0208@gmail.com";

	@BeforeClass
	public static void setUp() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
	}

	@After
	public void cleanUp() {

	}

	@Test
	public void genericFormTesting() {

		FormData formData = new FormData("Mr", "GetaQuoteFirstName", "GetaQuoteLastName", email, "0433615445", "Afternoon", "Email", "9999");
		System.out.println(formData);
		try {
	
			formURL = formURL + "?Vehicle=" + vehicle;
			driver.get(formURL);

			System.out.println("Testing get a quote form");

			Select formTitle = new Select(driver.findElement(By.xpath("//div/div/select[contains( @name,'title')]")));
			formTitle.selectByValue(formData.getTitle());
			
			driver.findElement(By.xpath("//div[2]/div/input")).sendKeys(formData.getFirstName());

			driver.findElement(By.xpath("//div[2]/div[3]/div/input")).sendKeys(formData.getLastName());
			
			driver.findElement(By.xpath("//div[2]/div[4]/div/input")).sendKeys(formData.getEmail());
			
			driver.findElement(By.xpath("//div[2]/div[5]/div/input")).sendKeys(formData.getPhone());
			
			Select preferredContactTime = new Select(driver.findElement(By.xpath("//div/div/select[contains( @name,'contactTime')]")));
			preferredContactTime.selectByValue(formData.getContactTime());
			
			
			Select preferredContactMethod = new Select(driver.findElement(By.xpath("//div/div/select[contains( @name,'contactMethod')]")));
			preferredContactMethod.selectByValue(formData.getContactMethod());
			
			driver.findElement(By.xpath("//form/div[2]/div[8]/div/input")).sendKeys(formData.getPostcode());

			Thread.sleep(2000);
			
			//wait the form response back
			while (!driver.findElement(By.xpath("//form/div[3]/div/div/div[3]/ul/li/label/h3")).isDisplayed())
			{
				System.out.println("Waiting...");
				Thread.sleep(2000);
			}
			
			String dealerName = driver.findElement(By.xpath("//form/div[3]/div/div/div[3]/ul/li/label/h3")).getText();
			assertNotNull(dealerName);
			
			//submit the form
			driver.findElement(By.xpath("//form/div[4]/div/button")).click();

			//wait the form response back
			while (!driver.findElement(By.xpath("//form/div[5]/div/div/div/h2")).isDisplayed())
			{
				System.out.println("Waiting...");
				Thread.sleep(2000);
			}
			
			String message = driver.findElement(By.xpath("//form/div[5]/div/div/div/h2")).getText();
			                                             
			assertEquals("Thank You!",message);
		
			
		} catch (Exception ex) {
			ex.printStackTrace();
			fail("the form can not processed , please contact administrator");
		}

	}

}
