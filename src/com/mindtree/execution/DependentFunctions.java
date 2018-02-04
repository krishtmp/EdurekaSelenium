package com.mindtree.execution;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class DependentFunctions extends TestReportGenerate {
	
	public DependentFunctions() {}

	public static WebDriver driver;
	
	static String reportfilename;

	@BeforeClass
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("http://newtours.demoaut.com/");
		updateDataFunction(Path,0, 0, "No of Test Cases to be executed");
		
		updateDataFunction(Path,0, 2, "Test Case Name");
		
		updateDataFunction(Path,1, 2, "Test Case Status - PASS/FAIL ");
		
		updateDataFunction(Path,0, 6, "Pass Percentage");
		
	}

	@AfterClass
	public void closeBrowser() {
		
		updateDataFunction(Path, 1, 0, String.valueOf(TestCaseCount));
		
		System.out.println("No of Passed Test cases: "+PassCount);
		
		System.out.println("Total no of test cases : "+TestCaseCount);
		
		float per = (float) PassCount / TestCaseCount;
		
		updateDataFunction(Path,1, 6, String.valueOf(per * 100));
		
		updateDataFunction(Path, 1, 8, String.valueOf(PassCount));
		
		updateDataFunction(Path,1, 9, String.valueOf(TestCaseCount - PassCount));
		
		driver.close();
	}

	@AfterSuite
	public void afterSuiteProcess() throws IOException, DocumentException {

		convertingtoPDFfile();
		sendingMailfunction();
	}

	private static void sendingMailfunction() {

		String from = "tmptesterid1@gmail.com", pass = "India123", to = "tmpkrish@gmail.com",
				subject = "TestSuiteReport", body = "Body";
		Properties props = System.getProperties();
		String host = "smtp.gmail.com";
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.user", from);
		props.put("mail.smtp.password", pass);
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
				return new javax.mail.PasswordAuthentication("tmptesterid1@gmail.com", "India123");
			}
		});
		MimeMessage message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject(subject);

			message.setText(body);

			BodyPart objMessageBodyPart = new MimeBodyPart();

			objMessageBodyPart.setText("Please Find The Attached Executed Test Report File ");

			Multipart multipart = new MimeMultipart();

			multipart.addBodyPart(objMessageBodyPart);

			objMessageBodyPart = new MimeBodyPart();

			String filename = "D:\\Workscape\\SeleniumProject\\" + reportfilename;

			FileDataSource source = new FileDataSource(filename);
			objMessageBodyPart.setDataHandler(new DataHandler(source));
			objMessageBodyPart.setFileName(filename);
			multipart.addBodyPart(objMessageBodyPart);
			message.setContent(multipart);
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, pass);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void convertingtoPDFfile() throws IOException, DocumentException {
		try {

			// Appending data time into Report filename
			Calendar calendar = Calendar.getInstance();
			SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

			FileInputStream input_document = new FileInputStream(new File(Path));

			XSSFWorkbook my_xls_workbook = new XSSFWorkbook(input_document);

			XSSFSheet my_worksheet = my_xls_workbook.getSheetAt(0);

			Iterator<Row> rowIterator = my_worksheet.iterator();

			Document iText_xls_2_pdf = new Document();

			reportfilename = "TestReportFile" + formater.format(calendar.getTime()) + ".pdf";

			System.out.println("Generated Report File name: "+reportfilename);

			PdfWriter.getInstance(iText_xls_2_pdf, new FileOutputStream(reportfilename));

			iText_xls_2_pdf.open();

			PdfPTable my_table = new PdfPTable(2);
			PdfPCell table_cell;
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next(); // Fetch CELL
					switch (cell.getCellType()) { // Identify CELL type
					case Cell.CELL_TYPE_STRING:
						table_cell = new PdfPCell(new Phrase(cell.getStringCellValue()));
						my_table.addCell(table_cell);
						break;
					}
				}
			}
			iText_xls_2_pdf.add(my_table);
			iText_xls_2_pdf.close();
			input_document.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
