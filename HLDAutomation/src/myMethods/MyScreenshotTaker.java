package myMethods;
/**
 * 
 * MyScreenshotTaker.java
 * Purpose: To take a screenshot of the web-page passed as an argument to this method. 
 * 
 * @author Gaurav Sharma
 * @params Webdriver instance.
 * @return Screenshot of the passed Webdriver instance
 * @version 1.0
 */

import java.io.File;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class MyScreenshotTaker {
	public static File scrshot ;
	public static File Screenshot (WebDriver mydriver) {
		scrshot = ((TakesScreenshot)mydriver).getScreenshotAs(OutputType.FILE);
		return scrshot;
	}
}
