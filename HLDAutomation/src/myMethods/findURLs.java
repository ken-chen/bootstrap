package myMethods;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class findURLs {

	public static void lonePageRoamer (String URL, WebDriver driver, String currentpath, String mainURL) throws IOException {
		int URLCount=0;
		String MainURL=mainURL;
		String CM_URL=URL;
		String UniqueURL_List=currentpath+"TxtFiles\\URLs\\Unique_URLs.txt";
		List<String> AllURLs = new ArrayList<String>();
		AllURLs.add(CM_URL);
		driver.get(CM_URL);
		System.out.println("Analysing Contents of page "+CM_URL);
		List<WebElement> HREFlist = null;
		HREFlist = driver.findElements(By.cssSelector(" a"));
		WriteToFile.myWriteAppend(UniqueURL_List,"");
		WriteToFile.myWriteAppend(UniqueURL_List,"Analysing Contents of page "+CM_URL);
		WriteToFile.myWriteAppend(UniqueURL_List,"");
		
		for (WebElement link : HREFlist) {
			if (!AllURLs.contains(link.getAttribute("href")) && link.getAttribute("href")!=null  && link.getAttribute("href").contains(MainURL))
			{
				System.out.println(link.getAttribute("href"));
				AllURLs.add(link.getAttribute("href"));
				WriteToFile.myWriteAppend(UniqueURL_List,(link.getAttribute("href")));
				URLCount=URLCount+1;
			}

		}
		WriteToFile.myWriteAppend(UniqueURL_List,"Valid Internal Links found: "+URLCount);
		System.out.println("Valid Internal Links found so far "+URLCount);
	}

	public static void pageRoamercomplete (String url, WebDriver driver, String currentpath) throws IOException {
		int URLCount=0;
		String main_URL=url;
		String uniqueURL_List=currentpath+"TxtFiles\\Unique_URLs.txt";
		List<String> allURLs = new ArrayList<String>();
		allURLs.add(main_URL);// Starting URL
		for(int i=0; i<allURLs.size(); i++) {
			driver.get(allURLs.get(i));
			System.out.println("Analysing Contents of page "+allURLs.get(i));
			List<WebElement> HREFlist = null;
			
			try {
				HREFlist = driver.findElements(By.cssSelector(" a"));
			//System.out.println("No HREF's for this URL "+AllURLs.get(i));
			for (WebElement link : HREFlist) {

				if (!(allURLs.contains(link.getAttribute("href"))) && (link.getAttribute("href")!=null) && (link.getAttribute("href").contains(main_URL)))
				{
					allURLs.add(link.getAttribute("href"));
					WriteToFile.myWriteAppend(uniqueURL_List,(link.getAttribute("href")));
					URLCount=URLCount+1;
				}

			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				}
			System.out.println("Valid Internal Links found so far "+URLCount);
			
		}
		
	}


}
