package com.isobar.projecttaskmanager.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;



public class SpreadsheetTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
	    generateExcelReport();
	}

	public static void generateExcelReport() throws FileNotFoundException,
			IOException {
		Workbook wb = new HSSFWorkbook();
	    //Workbook wb = new XSSFWorkbook();
	    CreationHelper createHelper = wb.getCreationHelper();
	    Sheet sheet = wb.createSheet("new sheet");

	    // Create a row and put some cells in it. Rows are 0 based.
	    Row row1 = sheet.createRow(0);

	    // Create a cell and put a date value in it.  The first cell is not styled
	    // as a date.
	    Cell cell1 = row1.createCell(0);
	    cell1.setCellValue("Project Task Manager Report");
	    
	    Row row2 = sheet.createRow(1);
	    Cell cell2 = row2.createCell(0);
	    cell2.setCellValue("Project Manager: " + "Tony Day");

	    Row row = sheet.createRow(2);	    
	    Cell cell = row.createCell(0);
	    // we style the second cell as a date (and time).  It is important to
	    // create a new cell style from the workbook otherwise you can end up
	    // modifying the built in style and effecting not only this cell but other cells.
	    CellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setDataFormat(
	        createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
	    cell = row.createCell(0);
	    cell.setCellValue("Date: " +new Date());
	    cell.setCellStyle( cellStyle);

	    //you can also set date as java.util.Calendar
//	    cell = row.createCell(2);
//	    cell.setCellValue(Calendar.getInstance());
//	    cell.setCellStyle(cellStyle);

	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
	}

}
