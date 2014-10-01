package myMethods;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.awt.Graphics2D; 
import java.awt.Image;
import java.awt.RenderingHints; 
import java.awt.image.BufferedImage; 
import java.awt.image.Raster; 
import java.io.File; 
import java.io.IOException;

import javax.imageio.ImageIO;


import org.apache.commons.io.FileUtils;

@SuppressWarnings("unused")
public class WriteToFile {
	static DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
	static Date date = new Date();
	static String newdate = dateFormat.format(date).toString();
	public static void myWriteAppend (String txtfile, String content) throws IOException {
		File file=new File(txtfile);

		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.append(content);
		bw.newLine();
		bw.close();
	}

	public static void myWriteEOF (String txtfile) throws IOException {
		File file=new File(txtfile);
		FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.newLine();
		bw.write("===== Run Completed=====");
		bw.newLine();
		bw.close();
	}

	public static void myWriteSOF (String txtfile) throws IOException {
		boolean fireup = (new File(txtfile)).mkdirs();
		File file=new File(txtfile);
		if(file.exists()==true)
		{
			file.delete();
		}
		if(file.exists()== false)
		{
			file.createNewFile();
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("File Created on "+newdate);
			bw.newLine();
			bw.newLine();
			bw.close();
		}
	}

	public static void myFileCompare(String GoldFile, String TestFile,String URL) throws IOException{
		int Flag=0;
		int Count=0;
		File GoldCopy=new File (GoldFile);
		File TestCopy=new File(TestFile);
		//System.out.println("Comparing Gold File "+GoldCopy+ " with Test File "+TestCopy);
		FileInputStream fstreamGold = new FileInputStream(GoldCopy);
		FileInputStream fstreamTest = new FileInputStream(TestCopy);
		DataInputStream InGold= new DataInputStream(fstreamGold);
		DataInputStream InTest= new DataInputStream(fstreamTest);
		BufferedReader BRGold = new BufferedReader(new InputStreamReader(InGold));
		BufferedReader BRTest = new BufferedReader(new InputStreamReader(InTest));

		String strGold, strTest;

		while ((strGold = BRGold.readLine()) != null && (strTest = BRTest.readLine()) != null)
		{
			Count=Count+1;
			//System.out.println(BRGold.readLine());
			//System.out.println(BRTest.readLine());
			boolean Myassertion=(strGold.equals(strTest));
			
			if((Myassertion==false)){
				System.out.println("Baseline Screenshot and Current Screenshot DO NOT Match for "+URL);
				System.out.println("Breaking point is on line :"+Count);
				System.out.println(FileUtils.readLines(GoldCopy).get(Count-1)+" v/s ");
				System.out.println(FileUtils.readLines(TestCopy).get(Count-1));
				System.out.println("::----------::");
				System.out.println("");
				Flag=1;
				BRTest.close();
				BRGold.close();				
				break;
			}
		}

		if (Flag==0)
		{
			System.out.println("TestCopy Matches GoldCopy for "+URL);
		}
		Flag=0;
	}
	public static void myFileInfo(File ImagePath) throws IOException{
		
		Image ImageFile=ImageIO.read(ImagePath);
		System.out.println(ImageFile.getGraphics());
		System.out.println(ImageFile.getSource());
		//ImageFile.getProperty(ImagePath);
	}
	
	public static void SOHTML(String Path) throws IOException{
		String Script="<!DOCTYPE html><html><title>Selenium Holden Automation Test Suite</title><script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script><script>function createData(){var NotTestedData=0;var PassData=0;var FailData=0;for(var i=0; i < document.myform.DDL.length; i++){var x=document.getElementById(\"mySelect\"+(i+1)).selectedIndex;var y=document.getElementById(\"mySelect\"+(i+1)).options;if (y[x].text==\"Not Tested\"){NotTestedData=NotTestedData+1;}if (y[x].text==\"Pass\"){PassData=PassData+1;}if (y[x].text==\"Fail\"){FailData=FailData+1;}document.getElementById(\"mySelect\"+(i+1)).disabled=true;}var data = google.visualization.arrayToDataTable([['Status', '# of tests'],['Pass',PassData ],['Fail', FailData],['NotTested',NotTestedData ]]);new google.visualization.PieChart(document.getElementById('visualization')).draw(data);document.getElementById(\"mySavebutton\").disabled = true;document.getElementById(\"myEditbutton\").disabled = false;var msg;var currentdate = new Date();var date= (currentdate.getDate() + \"/\" + (currentdate.getMonth()+1)  + \"/\" + currentdate.getFullYear() + \" @ \" + currentdate.getHours() + \":\" + currentdate.getMinutes() + \":\" + currentdate.getSeconds());var person=prompt(\"Who is testing this ?\",\"VJI_QA_Team_Member\");if (person!=null){msg=\"Results last saved by \" + person + \" on \"+date;document.getElementById(\"Savehistory\").innerHTML=msg;}}google.setOnLoadCallback(drawVisualization);</script><script type=\"text/javascript\" src=\"http://www.google.com/jsapi\"></script><script type=\"text/javascript\">google.load('visualization', '1', {packages: ['corechart']});</script><font face = \"verdana\"><img width=\"900\" height=\"160\" src=\"../../../../../../../../data/images/Holden_logo.png\" /><hr size=20 color= #800000><H2>Selenium Holden Automation Test Suite Result</H2><br><table width = \"100%\" border = \"4\" cellspacing = \"0\" cellpadding = \"0\" BGCOLOR =#A0A0A0><font face = \"verdana\"><tr><td width=\"70%\" ><table width = \"100%\" border = \"1\" cellspacing = \"0\" cellpadding = \"0\" BGCOLOR =#A0A0A0><tr><FORM id=\"myform\" name=\"myform\" action=\"\" ><td><b>#</td><td><b>Test URLs</td><td><b>CrossBrowser Layout Results</td><td><b>Screenshots</td><td><b>Status</td></tr>";
		WriteToFile.myWriteAppend(Path, Script);
		
	}
	
	public static void SOHTMLGEN(String Path, String url) throws IOException{
		String Script="<!DOCTYPE html><html><title>"+url+" Screenshots Test Suite</title><font face = \"verdana\"><img src=\"http://docs.seleniumhq.org/images/big-logo.png\" /><center><hr size=20 color= #800000><H2>Screenshots for URL "+url+" </H2><br><table border = \"4\" cellspacing = \"0\" cellpadding = \"0\" BGCOLOR =#A0A0A0><font face = \"verdana\"><tr><td><table border = \"2\" cellspacing = \"0\" cellpadding = \"0\" BGCOLOR =#A0A0A0><tr><td><center><b>#</td><td><b>Test URLs</td><td><b>Screenshots</td><td><b>Page Analysis</td><td><b>Text Grab</td></center></tr>";
		WriteToFile.myWriteAppend(Path, Script);
	}
	
	public static void SOHTMLGENERAL(String Path, String url) throws IOException{
		String Script="<!DOCTYPE html><html>" +
				"<title>"+url+" Screenshots Test Suite</title>" +
				"<body bgcolor =E5E4E2>" +
				"<font face = \"calibri\"><img src=\"http://docs.seleniumhq.org/images/big-logo.png\" />" +
				"<hr size=15 color= 48CCCD>" +
				"<center><H2>Screenshots for <u>"+url+" </u></H2><br>" +
				"<table border = \"0\" cellspacing = \"0\" cellpadding = \"0\" BGCOLOR =E5E4E2>" +
				"<font face = \"calibri\"><tr><td>" +
				"<table border = \"2\" cellspacing = \"0\" cellpadding = \"10\" BGCOLOR =53607E>" +
				"<tr align=\"center\">" +
				"<td><font size = 4 color=95B9C7><centre><b>#</td>" +
				"<td><font size = 4 color=95B9C7><b>Test URLs</td>" +
				"<td><font size = 4 color=95B9C7><b>Screenshots</td>" +
				"<td><font size = 4 color=95B9C7><b>Page Analysis</td>" +
				"<td><font size = 4 color=95B9C7><b>Text Grab</td></font>" +
				"</tr>";
		WriteToFile.myWriteAppend(Path, Script);
	}
	
	
	public static void SOHTMLHPW(String Path, String url) throws IOException{
		String Script="<!DOCTYPE html><html>" +
				"<title>"+url+" Screenshots Test Suite</title>" +
				"<body bgcolor =E5E4E2>" +
				"<font face = \"calibri\"><img src=\"http://docs.seleniumhq.org/images/big-logo.png\" />" +
				"<hr size=15 color= 48CCCD>" +
				"<center><H2>Screenshots for URL <u>"+url+" </u></H2><br>" +
				"<table border = \"0\" cellspacing = \"0\" cellpadding = \"0\" BGCOLOR =E5E4E2>" +
				"<font face = \"calibri\"><tr><td>" +
				"<table border = \"2\" cellspacing = \"0\" cellpadding = \"10\" BGCOLOR =53607E>" +
				"<tr align=\"center\">" +
				"<td><font size = 4 color=95B9C7><centre><b>#</td>" +
				"<td><font size = 4 color=95B9C7><b>Test URLs</td>" +
				"<td><font size = 4 color=95B9C7><b>Screenshots</td>" +
				"</font></tr>";
		WriteToFile.myWriteAppend(Path, Script);
	}
	
	public static void SOHTMLHPWBT(String Path, String url) throws IOException{
		String Script="<!DOCTYPE html><html>" +
				"<title>"+url+" Screenshots Test Suite</title>" +
				"<body bgcolor =E5E4E2>" +
				"<font face = \"calibri\"><img src=\"http://docs.seleniumhq.org/images/big-logo.png\" />" +
				"<hr size=15 color= 48CCCD>" +
				"<center><H2>Bluetooth Compatibility for <u>"+url+" </u></H2><br>" +
				"<table border = \"0\" cellspacing = \"0\" cellpadding = \"0\" BGCOLOR =E5E4E2>" +
				"<font face = \"calibri\"><tr><td>" +
				"<table border = \"2\" cellspacing = \"0\" cellpadding = \"10\" BGCOLOR =53607E>" +
				"<tr align=\"center\">" +
				"<td><font size = 4 color=95B9C7><centre><b>Vehicle Model</td>" +
				"<td><font size = 4 color=95B9C7><b>Phone Model</td>" +
				"<td><font size = 4 color=95B9C7><img width=\"60\" height=\"60\" src=\"http://png-2.findicons.com/files/icons/772/token_light/128/bluetooth.png\"/></td>" +
				"</font></tr>";
		WriteToFile.myWriteAppend(Path, Script);
		System.out.println("HTML log created");
	}
	public static void SOHTMLHPWR(String Path) throws IOException{
		String Script="<!DOCTYPE html><html>" +
				"<title>Screenshots Test Suite</title>" +
				"<body bgcolor =E5E4E2>" +
				"<font face = \"calibri\"><img src=\"http://docs.seleniumhq.org/images/big-logo.png\" />" +
				"<hr size=15 color= 48CCCD>" +
				"<center><H2>Screenshots for Holden Active Projects </u></H2><br>" +
				"<table border = \"0\" cellspacing = \"0\" cellpadding = \"0\" BGCOLOR =E5E4E2>" +
				"<font face = \"calibri\"><tr><td>" +
				"<table border = \"2\" cellspacing = \"0\" cellpadding = \"10\" BGCOLOR =53607E>" +
				"<tr align=\"center\">" +
				"<td><font size = 4 color=95B9C7><centre><b>#</td>" +
				"<td><font size = 4 color=95B9C7><b>Test URLs</td>" +
				"<td><font size = 4 color=95B9C7><b>Screenshots</td>" +
				"<td><font size = 4 color=95B9C7><b>Resources</td>" +
				"</font></tr>";
		WriteToFile.myWriteAppend(Path, Script);
	}
	
	public static void GraphHTML(String Path) throws IOException{
	String Graph="</table><table BGCOLOR =#A0A0A0><tr><td><br><input type=\"button\" id=\"mySavebutton\" onclick=\"createData()\" value=\"Save Results\"></td><td></td><td></td><td><br><input type=\"button\" id=\"myEditbutton\" onclick=\"this.disabled=true;document.getElementById('mySavebutton').disabled=false;\" value=\"Edit Results\" disabled></td></FORM></tr></table></td><td width=\"30%\" ><table width=\"100%\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\"BGCOLOR =\"white\"><tr><td><H2> Test Summary </H2></head><body style=\"font-family: verdana;border: 0 none;\"><div id=\"visualization\" style=\"width: 500px; height: 600px;\"></div></body></td></tr></table></td></font></font></table>";
 	WriteToFile.myWriteAppend(Path, Graph);
		
	}
	
	public static void EOHTML(String Path) throws IOException{
	String Content="</head>" +
			"<br>===========================================================<br>" +
			"Selenium Test Suite Complete<br>" +
			"===========================================================<br><br>" +
			"<font size=2><i><p id=\"Savehistory\"></p></i></font><br>" +
			"<P align=right><font size =1><i>Report Compiled For Holden<a href=\"http://www.visualjazz.com.au/\"><img width=\"90\" height=\"100\"src=\"../../../../../../../../data/images/footer_logo.png\" /></a><i></font></p>" +
			"</html>";
WriteToFile.myWriteAppend(Path, Content);
		
	}
	public static void EOHTMLGEN(String Path) throws IOException{
		String Content="<P align=right><font size =1><i>Report Compiled Using Java and Selenium Webdriver<a href=\"http://www.java.com\"><img width=\"90\" height=\"100\"src=\"http://www.youngwebbuilder.com/wp-content/uploads/2012/10/Java-logo.png\" /></a><i></font></p></table></head><br>===========================================================<br>Selenium Test Suite Complete<br>===========================================================</html>";
	WriteToFile.myWriteAppend(Path, Content);
			
		}
	
}


