package com.example.level01;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.MyPDDocument;
import com.example.MyPDPageContentStream;



/**
 * This class shows how to create a pdf document that looks like a ruler. In console, it will print the counter number and corresponding
 * x and y co-ordinates.
 * 
 * @author rmohta
 *
 */
public class PdfRuler {

	public static final float DEFAULT_FONT_SIZE = 10.0f;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		try(MyPDDocument document = this.createNewPDFDocument()) {
			createRulerDocument(document.getUnderlying(), null);
			Path outputFilePath = Files.createTempFile("Test-Ruler", ".pdf");
			document.getUnderlying().save(outputFilePath.toFile());
			logger.info("test# " + outputFilePath.toAbsolutePath().toString());
		} catch (IOException | COSVisitorException e) {
			logger.error("Exception while creating ruler document", e);
			fail(e.getMessage());
		}
	}
	
	/**
	 * Create a new empty document. Populates default information.
	 * <p>
	 * <strong>
	 *   Recommend to call this method within a try-with-resource block. That will ensure, close() is called when done with everything.
	 * </strong>
	 * 
	 * @throws IOException
	 */
	public MyPDDocument createNewPDFDocument() throws IOException {
		MyPDDocument document = new MyPDDocument();
		document.getUnderlying().getDocumentInformation().setAuthor("Rohit Mohta");
		document.getUnderlying().getDocumentInformation().setCreationDate(Calendar.getInstance());
		document.getUnderlying().getDocumentInformation().setSubject("Apache PDF Box Example");
		document.getUnderlying().getDocumentInformation().setTitle("PDF Ruler");
		return document;
	}
	
	/**
	 * 
	 * @param document instance of {@link PDDocument}
	 * @param pagesize
	 * @return updated instance of {@link PDDocument}
	 */
	public PDDocument createRulerDocument(PDDocument document, PDRectangle pagesize) throws IOException {
		if(pagesize == null) {
			pagesize = PDPage.PAGE_SIZE_A4;
		}
		PDPage page = new PDPage(pagesize);
		document.addPage(page);
		document.getDocumentInformation().setSubject("Ruler with 20.0f spacing");
		List<String> sb = new java.util.LinkedList<>();
		sb.add("counter,x coord,y coord");
		try(MyPDPageContentStream mycontentStream = new MyPDPageContentStream(document, page, true, false)) {
			PDPageContentStream contentStream = mycontentStream.getUnderlying();
			
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, DEFAULT_FONT_SIZE);
			if(logger.isTraceEnabled()) {
				logger.trace("page.getMediaBox() ==> " + page.getMediaBox().toString()); 
			}
			
			long counter = 0;
			for(float y = page.getMediaBox().getLowerLeftY(); y <= page.getMediaBox().getUpperRightY(); ) {
				for(float x = page.getMediaBox().getLowerLeftX(); x <= page.getMediaBox().getUpperRightX();) {
					sb.add(counter+","+x+","+y);
					//Starting and Closing is important
					contentStream.beginText();
						contentStream.moveTextPositionByAmount(x,y);
						contentStream.drawString(String.valueOf(counter));
					contentStream.endText();
					counter++;
					x += 50;
				}
				y += 50;
			}
		} catch (IOException e) {
			logger.error("Exception while creating a ruler document", e);
		}
		if(logger.isTraceEnabled()) {
			StringBuilder toPrint = new StringBuilder("\n Time: " + new Date().toString());
			toPrint.append("\n --------------------------------------------------------------------------\n");
			for(String line: sb) {
				String[] split = line.split(",");
				toPrint.append(String.format("\n| %-30s |%-12s | %-12s |",
						split[0], split[1], split[2] 
						));
			}
			toPrint.append("\n --------------------------------------------------------------------------\n");
			logger.trace(toPrint.toString());
			toPrint = null;
		}
		sb.clear();
		sb = null;
		return document;
	}


}
