package com.isobar.projecttaskmanager.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class SimpleImages {
	 public static void main(String[] args) throws Exception {
	        XWPFDocument doc = new XWPFDocument();
	        XWPFParagraph p = doc.createParagraph();

	        XWPFRun r = p.createRun();

	     
	            int format = 0;
	            String imgFile = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\0Resources_Screenshot2.jpeg";
				
	            //String imgFile = "";
				if(imgFile.endsWith(".emf")) format = XWPFDocument.PICTURE_TYPE_EMF;
	            else if(imgFile.endsWith(".wmf")) format = XWPFDocument.PICTURE_TYPE_WMF;
	            else if(imgFile.endsWith(".pict")) format = XWPFDocument.PICTURE_TYPE_PICT;
	            else if(imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) format = XWPFDocument.PICTURE_TYPE_JPEG;
	            else if(imgFile.endsWith(".png")) format = XWPFDocument.PICTURE_TYPE_PNG;
	            else if(imgFile.endsWith(".dib")) format = XWPFDocument.PICTURE_TYPE_DIB;
	            else if(imgFile.endsWith(".gif")) format = XWPFDocument.PICTURE_TYPE_GIF;
	            else if(imgFile.endsWith(".tiff")) format = XWPFDocument.PICTURE_TYPE_TIFF;
	            else if(imgFile.endsWith(".eps")) format = XWPFDocument.PICTURE_TYPE_EPS;
	            else if(imgFile.endsWith(".bmp")) format = XWPFDocument.PICTURE_TYPE_BMP;
	            else if(imgFile.endsWith(".wpg")) format = XWPFDocument.PICTURE_TYPE_WPG;
	            else {
	                System.err.println("Unsupported picture: " + imgFile +
	                        ". Expected emf|wmf|pict|jpeg|png|dib|gif|tiff|eps|bmp|wpg");
	      
	            }

	            r.setText(imgFile);
	            r.addBreak();
	            r.addPicture(new FileInputStream(imgFile), format, imgFile, Units.toEMU(200), Units.toEMU(200)); // 200x200 pixels
	            r.addBreak(BreakType.PAGE);
	        

	        FileOutputStream out = new FileOutputStream("images.docx");
	        doc.write(out);
	        out.close();
	    }
}
