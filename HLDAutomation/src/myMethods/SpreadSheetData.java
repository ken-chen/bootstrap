package myMethods;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 
 * SpreadSheetData.java
 * Purpose: 
 * 1. This class will read data from an excel sheet and run parameterised tests. 
 * 
 * @author Gaurav Sharma
 * @version 1.0 12/12/2012
 */

/**
 * Read data in an Excel spreadsheet and return it as a collection of objects.
 * This is designed to facilitate for parameterized tests in JUnit that
 * get data from an excel spreadsheet.
 * @author Gaurav Sharma
 * @params nce.
 * @return Screenshot of the passed Webdriver instance
 * @version 1.0
 */
public class SpreadSheetData {
	
	private transient Collection<Object[]> data = null;

    public SpreadSheetData(final InputStream excelInputStream) throws IOException {
        this.data = loadFromSpreadsheet(excelInputStream);
    }

    public Collection<Object[]> getData() {
        return data;
    }

    private Collection<Object[]> loadFromSpreadsheet(final InputStream excelFile)
            throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook(excelFile);

        data = new ArrayList<Object[]>();
        Sheet sheet = workbook.getSheetAt(0);

        int numberOfColumns = countNonEmptyColumns(sheet);
        List<Object[]> rows = new ArrayList<Object[]>();
        List<Object> rowData = new ArrayList<Object>();
        for (Row row : sheet) {
        	int rownum = row.getRowNum();
        	System.out.println("Data fetched from rownum: " + rownum);
            if (isEmpty(row)) {
                break;
            } else {
            	if (rownum >= 1){
                rowData.clear();
                for (int column = 0; column < numberOfColumns; column++) {
                	
                    Cell cell = row.getCell(column);
                    rowData.add(objectFrom(workbook, cell));
                }
                rows.add(rowData.toArray());
            	}
            }
        }
        return rows;
    }

    private boolean isEmpty(final Row row) {
        Cell firstCell = row.getCell(0);
        boolean rowIsEmpty = (firstCell == null)
                || (firstCell.getCellType() == Cell.CELL_TYPE_BLANK);
        return rowIsEmpty;
    }

    private int countNonEmptyColumns(final Sheet sheet) {
        Row firstRow = sheet.getRow(0);
        return firstEmptyCellPosition(firstRow);
    }

    private int firstEmptyCellPosition(final Row cells) {
        int columnCount = 0;
        for (Cell cell : cells) {
            if (cell.getCellType() == Cell.CELL_TYPE_BLANK) {
                break;
            }
            columnCount++;
        }
        return columnCount;
    }

    private Object objectFrom(final HSSFWorkbook workbook, final Cell cell) {
        Object cellValue = null;

        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
        	//System.out.println("CELL_TYPE_STRING: ");
            cellValue = cell.getRichStringCellValue().getString();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
        	//System.out.println("CELL_TYPE_NUMERIC: ");
            cellValue = getNumericCellValue(cell);
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
        	//System.out.println("CELL_TYPE_BOOLEAN: ");
            cellValue = cell.getBooleanCellValue();
        } else if (cell.getCellType()  ==Cell.CELL_TYPE_FORMULA) {
        	//System.out.println("CELL_TYPE_FORMULA: ");
            cellValue = evaluateCellFormula(workbook, cell);
        }

        return cellValue;
    
    }

    private Object getNumericCellValue(final Cell cell) {
        Object cellValue;
        if (DateUtil.isCellDateFormatted(cell)) {
            cellValue = new Date(cell.getDateCellValue().getTime());
        } else {
            cellValue = cell.getNumericCellValue();
        }
        return cellValue;
    }

    private Object evaluateCellFormula(final HSSFWorkbook workbook, final Cell cell) {
        FormulaEvaluator evaluator = workbook.getCreationHelper()
                .createFormulaEvaluator();
        CellValue cellValue = evaluator.evaluate(cell);
        Object result = null;
        
        if (cellValue.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            result = cellValue.getBooleanValue();
        } else if (cellValue.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            result = cellValue.getNumberValue();
        } else if (cellValue.getCellType() == Cell.CELL_TYPE_STRING) {
            result = cellValue.getStringValue();   
        }
        
        return result;
    }
}
