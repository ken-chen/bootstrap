package com.isobar.projecttaskmanager.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.TextAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

public class SimpleDocument {
    public static void main(String[] args) throws Exception {
        XWPFDocument doc = new XWPFDocument();
        String saveLocation = "C:\\Output\\Screenshots\\HoldenProjectWork\\11Jan2014\\Run#23\\0Resources_Screenshot.jpg";
		
		doc.createParagraph().createRun().addPicture(new FileInputStream(saveLocation), Document.PICTURE_TYPE_JPEG, saveLocation, Units.toEMU(200), Units.toEMU(200));
       
		
		XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);
        p1.setVerticalAlignment(TextAlignment.TOP);

        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setText("The quick brown fox");
        r1.setBold(true);
        r1.setFontFamily("Courier");
        r1.setUnderline(UnderlinePatterns.DOT_DOT_DASH);
        //r1.setTextPosition(100);

        XWPFParagraph p3 = doc.createParagraph();
        XWPFRun r5 = p3.createRun();
        r5.setTextPosition(-10);
        r5.setText("For in that sleep of death what dreams may come");
        r5.addCarriageReturn();
//        r5.setText("When we have shuffled off this mortal coil,"
//                + "Must give us pause: there's the respect"
//                + "That makes calamity of so long life;");
//        r5.addBreak();
//        r5.setText("For who would bear the whips and scorns of time,"
//                + "The oppressor's wrong, the proud man's contumely,");
//        
//        r5.addBreak(BreakClear.ALL);
//        r5.setText("The pangs of despised love, the law's delay,"
//                + "The insolence of office and the spurns" + ".......");

        String imageName = saveLocation;

		//doc.createParagraph().createRun().addPicture(new FileInputStream(saveLocation),doc.PICTURE_TYPE_JPEG,imageName, 328, 247);
        
        FileOutputStream out = new FileOutputStream("simple.docx");
        doc.write(out);
        out.close();
        
//        CustomXWPFDocument document = new CustomXWPFDocument();
//        int id = document.addPicture(new FileInputStream(new File("c:\\test.jpg")), Document.PICTURE_TYPE_JPEG);
//        document.createPicture(id, 259, 58);
//
//        id = document.addPicture(new FileInputStream(new File("c:\\test.png")), Document.PICTURE_TYPE_PNG);
//        document.createPicture(id, 400, 200);
//        
//        CustomXWPFDocument document = new CustomXWPFDocument(new FileInputStream(new File("doc1.docx")));
//        FileOutputStream fos = new FileOutputStream(new File("doc2.docx"));
//        String id = document.addPictureData(new FileInputStream(new File(saveLocation)), Document.PICTURE_TYPE_JPEG);
//        document.createPicture(id,document.getNextPicNameNumber(Document.PICTURE_TYPE_JPEG), 64, 64);
//        document.write(fos);
//        fos.flush();
//        fos.close();

    }
}
