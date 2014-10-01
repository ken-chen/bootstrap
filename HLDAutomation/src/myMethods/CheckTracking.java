package myMethods;

import java.io.IOException;

/**
 * 
 * CheckTracking.java
 * Purpose:  
 *         
 * @author Gaurav Sharma
 * @params 
 * @return Current Run for that project folder.
 * @version 1.0
 */

public class CheckTracking {
	static String tracking_id;
	public static String CheckErrorsComplete (String CurrURL) throws IOException {
		String myURL=CurrURL;
		int flag =1;
		
			
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
		return(tracking_id);
		
	}

	

}



