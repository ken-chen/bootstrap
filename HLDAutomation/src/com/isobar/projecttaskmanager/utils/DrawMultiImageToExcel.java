package com.isobar.projecttaskmanager.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;

public class DrawMultiImageToExcel {
	public static void main(String[] args) throws IOException {

		generateExcelReport();
	}

	public static void generateExcelReport() throws FileNotFoundException,
			IOException {
		//create a new workbook
	    HSSFWorkbook wb = new HSSFWorkbook();
	    CreationHelper helper = wb.getCreationHelper();

	    //create sheet
	    Sheet sheet = wb.createSheet();

	    // Create the drawing patriarch.  This is the top level container for all shapes. 
	    Drawing drawing = sheet.createDrawingPatriarch();


	    String imagename1 = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\0Resources_Screenshot.jpg";   
	    String imagename2 = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\1Resources_Screenshot.jpg";
	    int pictureIdx = prepareAddImageSTOHSSFworkbook(wb, imagename1);
	    int pictureIdx2 = prepareAddImageSTOHSSFworkbook(wb, imagename2);
	    
	    addmagesToExcel(helper, drawing, pictureIdx,2,3);

	    addmagesToExcel(helper, drawing, pictureIdx2,2,73);

	    
	    
	    //save workbook
	    String file = "report.xls";
	    //if(wb instanceof XSSFWorkbook) file += "x";
	    FileOutputStream fileOut = new FileOutputStream(file);
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

	private static int prepareAddImageSTOHSSFworkbook(HSSFWorkbook wb,
			String imagename) throws FileNotFoundException, IOException {
		//add picture data to this workbook.
	    InputStream is = new FileInputStream(imagename);
	    byte[] bytes = IOUtils.toByteArray(is);
	    int pictureIdx = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
	    is.close();
		return pictureIdx;
	}
}
