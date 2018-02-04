package com.mindtree.execution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestReportGenerate extends TestListenerAdapter {

	public TestReportGenerate() {
	}

	static int column = 0, rowN = 3, PassCount, TestCaseCount;
	String Path = "D:\\Workscape\\SeleniumProject\\Book1.xlsx";

	@Override
	public void onTestFailure(ITestResult result) {

		updateDataFunction(Path, column, rowN, result.getMethod().getMethodName().toUpperCase());

		updateDataFunction(Path, column + 1, rowN, "FAIL");

		rowN++;

		TestReportGenerate.TestCaseCount = TestCaseCount + 1;

	}

	@Override
	public void onTestSuccess(ITestResult result) {


		updateDataFunction(Path, column, rowN, result.getMethod().getMethodName().toUpperCase());

		updateDataFunction(Path, column + 1, rowN, "PASS");

		rowN++;

		System.out.println("PASS");

		TestReportGenerate.PassCount = PassCount + 1;

		TestReportGenerate.TestCaseCount = TestCaseCount + 1;
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		System.out.println("Skipped");
	}

	static XSSFWorkbook workbook = new XSSFWorkbook();
	static XSSFSheet sheet;

	public static void writetoexcel(String ExcelName) {

		try {
			FileOutputStream out = new FileOutputStream(new File(ExcelName));
			workbook.write(out);
			out.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateDataFunction(String ExcelName, int ColNum, int RowNum, String Result) {
		try {
			FileInputStream fis = new FileInputStream(new File(ExcelName));
			workbook = new XSSFWorkbook(fis);
			sheet = workbook.getSheetAt(0);
			XSSFCell cell;
			XSSFRow row = null;
			row = sheet.getRow(RowNum);
			if (row == null) {
				row = sheet.createRow(RowNum);
			}

			cell = row.getCell(ColNum, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);

			if (cell == null) {
				cell = row.createCell(ColNum);
				cell.setCellValue(Result);

			} else {
				cell.setCellValue(Result);

			}
			//System.out.println(sheet.getRow(RowNum).getCell(ColNum).getStringCellValue());
			writetoexcel(ExcelName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
