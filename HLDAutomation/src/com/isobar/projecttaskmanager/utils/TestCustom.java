package com.isobar.projecttaskmanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xwpf.usermodel.Document;

public class TestCustom {
	 public static void main(String []a) throws FileNotFoundException, IOException, InvalidFormatException
	    {
	        String imagename = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\0Resources_Screenshot.jpg";
			
		 
	        CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File("C:\\Output\\new.docx")));
	        FileOutputStream fos = new FileOutputStream(new File("C:\\Output\\new.docx"));

	        String blipId = document.addPictureData(new FileInputStream(new File(imagename)), Document.PICTURE_TYPE_JPEG);

	        System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG));

	        //System.out.println(document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG));
	        document.createPicture(blipId,document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG), 500, 500);


	        document.write(fos);
	        fos.flush();
	        fos.close();

	    }
}
