package com.example;

import java.io.Closeable;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

/**
 * To make use of the try-with-resource, implements java.io.Closeable. For PDFBox Version: 1.8
 * 
 * @author rmohta
 * 
 */
public class MyPDPageContentStream implements Closeable {

	protected org.apache.pdfbox.pdmodel.edit.PDPageContentStream holder = null;
	
	/**
     * Create a new PDPage content stream. 
     * <b>Append</b> is deactivated (false) by default.
     *
     * @param document The document the page is part of.
     * @param sourcePage The page to write the contents to.
     * @throws IOException If there is an error writing to the page contents.
     */
    public MyPDPageContentStream(PDDocument document, PDPage sourcePage) throws IOException
    {
        this.holder = new org.apache.pdfbox.pdmodel.edit.PDPageContentStream(document,sourcePage);
    }
    
    /**
     * Create a new PDPage content stream.
     *
     * @param document The document the page is part of.
     * @param sourcePage The page to write the contents to.
     * @param appendContent Indicates whether content will be overwritten. If false all previous content is deleted.
     * @param compress Tell if the content stream should compress the page contents.
     * @throws IOException If there is an error writing to the page contents.
     */
    public MyPDPageContentStream(PDDocument document, PDPage sourcePage, boolean appendContent, boolean compress)
            throws IOException
    {
    	this.holder = new org.apache.pdfbox.pdmodel.edit.PDPageContentStream(document, sourcePage, appendContent, compress);
    }

    
    public org.apache.pdfbox.pdmodel.edit.PDPageContentStream getUnderlying() {
    	return holder;
    }
    
    @Override
    public void close() throws IOException {
    	if(this.holder != null) {
    		this.holder.close();
    	}
    }
    
}
