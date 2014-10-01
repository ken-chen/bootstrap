package com.isobar.projecttaskmanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

import com.isobar.projecttaskmanager.pojo.SocialMedia;

public class CutomFileUtils {
	public static String getfileSavedLocation(String path, String currURL,
			String count, String name, File file) throws IOException {
		// String URL=currURL;
		File myFile = file;
		String CurrentPath = path;
		String Name = name;

		// System.out.println("Screenshot for URL "+URL+" saved in Folder: "+CurrentPath);
		FileUtils.copyFile(myFile, new File(CurrentPath + count + Name
				+ "_Screenshot.jpg"));
		return (CurrentPath + count + Name + "_Screenshot.jpg");

	}
public static void main(String[] args) throws FileNotFoundException, IOException {

	List<String> urls= new ArrayList<String>();
	urls.add("C:\\Output\\Screenshots\\HoldenProjectWork\\12Jan2014\\Run#1\\0Resources_Screenshot.jpg");
	urls.add("C:\\Output\\Screenshots\\HoldenProjectWork\\12Jan2014\\Run#1\\1Resources_Screenshot.jpg");
	urls.add("C:\\Output\\Screenshots\\HoldenProjectWork\\12Jan2014\\Run#1\\2Resources_Screenshot.jpg");
	urls.add("C:\\Output\\Screenshots\\HoldenProjectWork\\12Jan2014\\Run#1\\3Resources_Screenshot.jpg");
	urls.add("C:\\Output\\Screenshots\\HoldenProjectWork\\12Jan2014\\Run#1\\4Resources_Screenshot.jpg");
	generateExcelReport("Tony Day", urls);
}
	public static void generateExcelReport(String name, List<String> imageList) throws FileNotFoundException,
			IOException {
	//	name = "Tony Day";
		//ojectWork\12Jan2014\Run#1\5Resources_Screenshot.jpg
//	    String imagename1 = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\0Resources_Screenshot.jpg";   
//	    String imagename2 = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\1Resources_Screenshot.jpg";
//	
		
		Workbook wb = new HSSFWorkbook();
		// Workbook wb = new XSSFWorkbook();
		CreationHelper createHelper = wb.getCreationHelper();
		Sheet sheet = wb.createSheet("Project Task Manager Report");

		// Create a row and put some cells in it. Rows are 0 based.
		Row row1 = sheet.createRow(0);

		// Create a cell and put a date value in it. The first cell is not
		// styled
		// as a date.
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue("Project Task Manager Report");

		Row row2 = sheet.createRow(1);
		Cell cell2 = row2.createCell(0);
		cell2.setCellValue("Project Manager: " + name);

		Row row = sheet.createRow(2);
		Cell cell = row.createCell(0);
		// we style the second cell as a date (and time). It is important to
		// create a new cell style from the workbook otherwise you can end up
		// modifying the built in style and effecting not only this cell but
		// other cells.
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				"m/d/yy h:mm"));
		cell = row.createCell(0);
		cell.setCellValue("Date: " + new Date());
		cell.setCellStyle(cellStyle);

		// you can also set date as java.util.Calendar
		// cell = row.createCell(2);
		// cell.setCellValue(Calendar.getInstance());
		// cell.setCellStyle(cellStyle);


		// Create the drawing patriarch.  This is the top level container for all shapes. 
	    Drawing drawing = sheet.createDrawingPatriarch();


//	    String imagename1 = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\0Resources_Screenshot.jpg";   
//	    String imagename2 = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\1Resources_Screenshot.jpg";
//	   
	    //level1
	    int rowNum =4;
	    for (String imagename: imageList){
	    	
	        addmagesToExcel(createHelper, drawing, prepareAddImageSTOHSSFworkbook(wb, imagename),0,rowNum);
	    	if(imagename.contains("short")){
	    		   rowNum += 50;
	    	}else if(imagename.contains("level1")){
	    		  rowNum += 85;
	    	}else{
	    		   rowNum += 65;
	    	}
	    	
	     
	    }
//	    int pictureIdx = prepareAddImageSTOHSSFworkbook(wb, imagename1);
//	    int pictureIdx2 = prepareAddImageSTOHSSFworkbook(wb, imagename2);
//	    
//	    addmagesToExcel(createHelper, drawing, pictureIdx,0,4);
//
//	    addmagesToExcel(createHelper, drawing, pictureIdx2,0,74);

		    
		  
		
		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("PTM Report For " +name+" .xls");
		wb.write(fileOut);
		fileOut.close();
	}
	
	
	
	
	
	
	private static void addmagesToExcel(CreationHelper helper, Drawing drawing,
			int pictureIdx,int coll,int rowl) {
		//add a picture shape
	    ClientAnchor anchor = helper.createClientAnchor();
	    //set top-left corner of the picture,
	    //subsequent call of Picture#resize() will operate relative to it
	    anchor.setCol1(coll);
	    anchor.setRow1(rowl);
	    Picture pict = drawing.createPicture(anchor, pictureIdx);

	    //auto-size picture relative to its top-left corner
	    pict.resize();
	}

	private static int prepareAddImageSTOHSSFworkbook(Workbook wb,
			String imagename) throws FileNotFoundException, IOException {
		//add picture data to this workbook.
	    InputStream is = new FileInputStream(imagename);
	    byte[] bytes = IOUtils.toByteArray(is);
	    int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
	    is.close();
		return pictureIdx;
	}
	
	
	/**
	 * @param socialMedias
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void generateExcelReportForSocial(List<SocialMedia> socialMedias) throws FileNotFoundException,
	IOException {
		
		Workbook wb = new HSSFWorkbook();

		Sheet sheet = wb.createSheet("Social Media for Holden Australia");
		
		// Create the first  row and put some cells(headings) in it. Rows are 0 based.
		Row row1 = sheet.createRow(0);
		
		// Create a cell and put a date value in it. 
		Cell cell1 = row1.createCell(0);
		cell1.setCellValue("Page URL");
		
		Cell cell2 = row1.createCell(1);
		cell2.setCellValue("FaceBook Title");
		
		Cell cell3 = row1.createCell(2);
		cell3.setCellValue("FaceBook Description");
		
		Cell cell4 = row1.createCell(3);
		cell4.setCellValue("FaceBook Image URL");
		
		Cell cell5 = row1.createCell(4);	
		cell5.setCellValue("Tweet Message");
		
		Cell cell6 = row1.createCell(5);	
		cell6.setCellValue("Tweet URL");
		
		Cell cell7 = row1.createCell(6);	
		cell7.setCellValue("Tweet HashTag");
		
		Cell cell8 = row1.createCell(7);	
		cell8.setCellValue("Google Plus Title");
		
		Cell cell9 = row1.createCell(8);	
		cell9.setCellValue("Google Plus Image URL");
		
		Cell cell10 = row1.createCell(9);	
		cell10.setCellValue("Send To Friend Message");
		
		for(int i=0; i < socialMedias.size(); i++ ){
			
			SocialMedia sm = socialMedias.get(i);
			Row row = sheet.createRow(i+1);
			
			Cell pageURLCell = row.createCell(0);
			pageURLCell.setCellValue(sm.getPageUrl());
			
			Cell facebookTitleCell = row.createCell(1);
			facebookTitleCell.setCellValue(sm.getFacebook_title());
			
			Cell facebookDescriptionCell = row.createCell(2);
			facebookDescriptionCell.setCellValue(sm.getFacebook_description());
			
			Cell facebookImageCell = row.createCell(3);
			facebookImageCell.setCellValue(sm.getFacebook_image());
			
			Cell tweetMessgaeCell = row.createCell(4);
			tweetMessgaeCell.setCellValue(sm.getTweet_message());
			
			Cell tweetURLCell = row.createCell(5);
			tweetURLCell.setCellValue(sm.getTweet_url());
			
			Cell tweetHashTagCell = row.createCell(6);
			tweetHashTagCell.setCellValue(sm.getTweet_hashTag());
			
			Cell googlePlusTitleCell = row.createCell(7);
			googlePlusTitleCell.setCellValue(sm.getGooglePlus_title());
			
			Cell googlePlusImageCell = row.createCell(8);
			googlePlusImageCell.setCellValue(sm.getGooglePlus_image());
			
			Cell sendToFriendMessageCell = row.createCell(9);
			sendToFriendMessageCell.setCellValue(sm.getSendToFriend_message());
			

		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream("Social Media Holden Australia_"+System.currentTimeMillis() +".xls");
		
		//write the content to the excel files
		wb.write(fileOut);
		fileOut.close();
		}


	
}
