/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.pkohub.gradle.flyingsaucer;

import com.lowagie.text.DocumentException;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * Converter for valid HTML to PDF.
 * @author Philipp Kolmhofer
 */
public class Html2PdfConverter {
    
    private final String fullResourceBasePath;
    
    /**
     * Construct a new instance.
     * @param resPath Resource path for additional files like css, images, ...
     */
    public Html2PdfConverter(String resPath) {
        this.fullResourceBasePath = resPath;
    }
    
    public void doRender(File fInput, File fOutput) {
        throw new RuntimeException("Not implemented");
    }
    
    /**
     * Render a document.
     * @param inputStream InputStream
     * @param outputStream OutputStream
     * @param cleanUp if true, JTidy is used to check and clean html before conversion
     * @throws DocumentException if the PDF could not be created 
     */
    public void doRender(InputStream inputStream, OutputStream outputStream, Boolean cleanUp) throws DocumentException {
        ITextRenderer renderer = new ITextRenderer();
        SharedContext sharedContext = renderer.getSharedContext();
        if (!fullResourceBasePath.isEmpty())
            sharedContext.setBaseURL(fullResourceBasePath);
        
        String inputString;
        if (cleanUp) {
            ByteArrayOutputStream inpTidy = new ByteArrayOutputStream();
            Tidy tidy = new Tidy();
            tidy.setXHTML(true);
            tidy.setShowWarnings(false);
            tidy.setShowErrors(0);
            tidy.parse(inputStream, inpTidy);
            inputString = inpTidy.toString();
        }
        else {
            inputString = getStringFromInputStream(inputStream);
        }

        renderer.setDocumentFromString(inputString);
        renderer.layout();

        renderer.createPDF(outputStream, true);
    }
    
    /**
     * Read string from InputStream.
     * @param is InputStream
     * @return String
     */
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();
    }
	
}
