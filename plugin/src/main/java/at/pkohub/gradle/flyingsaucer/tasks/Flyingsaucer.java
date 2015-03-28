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
package at.pkohub.gradle.flyingsaucer.tasks;

import at.pkohub.gradle.flyingsaucer.Html2PdfConverter;
import com.lowagie.text.DocumentException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import org.gradle.api.DefaultTask;
import org.gradle.api.InvalidUserDataException;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

/**
 * Task implementation.
 * @author Philipp Kolmhofer
 */
public class Flyingsaucer extends DefaultTask {
    
    /**
     * Description shown in gradle tasks.
     */
    public static String DESCRIPTION = "Convert HTML files to PDF using the FlyingSaucer library.";
    
    /**
     * Defines the output directory.
     */
    @OutputDirectory
    private File outDir;
    
    /**
     * Defines the context path for resources like images, css files, ...
     */
    @InputDirectory
    private File contextPath;
    
    /**
     * If true, JTidy is used to check for a valid XHTML and convert invalid files. 
     * Defaults to true.
     */
    @Input
    @Optional
    private Boolean cleanHtml = true;
    
    /**
     * Defines the list of input html files.
     */
    @InputFiles
    private File[] inputFiles;
    
    @TaskAction
    public void flyingsaucer() throws FileNotFoundException, DocumentException {
        
        validateInput();
                
        Html2PdfConverter converter = new Html2PdfConverter(contextPath.getAbsolutePath());
        
        for (File inputFile : inputFiles) {
            InputStream i = new FileInputStream(inputFile);
            File curOutFile = new File(outDir.getAbsolutePath() + "/" + inputFile.getName().concat(".pdf"));
            OutputStream out = new FileOutputStream(curOutFile);
            converter.doRender(i, out, cleanHtml);
        }
    }
    
    /**
     * Validates input and throws an exception if.
     */
    protected void validateInput() {
        if (inputFiles == null || inputFiles.length == 0)
            throw new InvalidUserDataException("No inputFiles specified");
        
        if (contextPath == null || !contextPath.isDirectory())
            throw new InvalidUserDataException("contextPath must be a directory");
        
        if (outDir == null || !outDir.isDirectory())
            throw new InvalidUserDataException("outDir must be a directory");
    }

    public File getOutDir() {
        return outDir;
    }

    public void setOutDir(File outDir) {
        this.outDir = outDir;
    }

    public File getContextPath() {
        return contextPath;
    }

    public void setContextPath(File contextPath) {
        this.contextPath = contextPath;
    }

    public Boolean getCleanHtml() {
        return cleanHtml;
    }

    public void setCleanHtml(Boolean cleanHtml) {
        this.cleanHtml = cleanHtml;
    }

    public File[] getInputFiles() {
        return inputFiles;
    }

    public void setInputFiles(File[] inputFiles) {
        this.inputFiles = inputFiles;
    }
}
