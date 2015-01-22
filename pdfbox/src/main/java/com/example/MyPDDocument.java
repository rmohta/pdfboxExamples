package com.example;

import java.io.Closeable;
import java.io.IOException;

/**
 * To make use of the try-with-resource, implements java.io.Closeable. For PDFBox Version: 1.8
 * @author rmohta
 *
 */
public class MyPDDocument implements Closeable {

	// Hold the initial x position. 
	protected final static float X0 = 5f; 

	// Hold the padding bottom of the document. 
	protected final static float PADDING_BOTTOM_OF_DOCUMENT = 30f; 
	protected org.apache.pdfbox.pdmodel.PDDocument holder = null;
	
    /**
     * Constructor, creates a new PDF Document with no pages.  You need to add
     * at least one page for the document to be valid.
     *
     * @throws IOException If there is an error creating this document.
     */
	public MyPDDocument() throws IOException 
	{
		this.holder = new org.apache.pdfbox.pdmodel.PDDocument();
	}
	
    public org.apache.pdfbox.pdmodel.PDDocument getUnderlying() {
    	return holder;
    }
    
    @Override
    public void close() throws IOException {
    	if(this.holder != null) {
    		this.holder.close();
    	}
    }
    
/*    @SuppressWarnings("rawtypes") 
	private void addReportFooter(final PDDocument doc, final String reportName)
			throws IOException {

		PDPageContentStream footercontentStream = null;
		try {

			List pages = doc.getDocumentCatalog().getAllPages();

			for (int i = 0; i < pages.size(); i++) {

				PDPage page = ((PDPage) pages.get(i));
				footercontentStream = new PDPageContentStream(doc, page, true,
						true);
				footercontentStream.beginText();
				footercontentStream.setFont(PDType1Font.HELVETICA_BOLD,
						FONT_SIZE);
				footercontentStream
						.moveTextPositionByAmount(
								X0,
								(PDPage.PAGE_SIZE_A4.getLowerLeftY() + PADDING_BOTTOM_OF_DOCUMENT));
				footercontentStream.drawString(reportName);
				footercontentStream.moveTextPositionByAmount(
						(PDPage.PAGE_SIZE_A4.getUpperRightX() / 2),
						(PDPage.PAGE_SIZE_A4.getLowerLeftY()));
				footercontentStream.drawString((i + 1) + " - " + pages.size());
				footercontentStream.endText();
				footercontentStream.close();

			}
		} catch (final IOException exception) {
			throw new RuntimeException(exception);
		} finally {

			if (footercontentStream != null) {
				try {
					footercontentStream.close();
				} catch (final IOException exception) {
					throw new RuntimeException(exception);
				}

			}
		}
	}*/

}
