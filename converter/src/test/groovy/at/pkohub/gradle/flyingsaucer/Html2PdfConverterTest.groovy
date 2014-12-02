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
package at.pkohub.gradle.flyingsaucer

import spock.lang.Specification

/**
 * Tests for Html2PdfConverter.
 * 
 * @author Philipp Kolmhofer
 */
class Html2PdfConverterTest extends Specification {
    
    def "Error thrown on invalid input without cleaning"() {
        setup:
            def renderer = new Html2PdfConverter("")
            def input = "<html><body><p>Test</p></body>"
            def cleanHtml = Boolean.FALSE
            ByteArrayInputStream inp = new ByteArrayInputStream(input.getBytes())
            ByteArrayOutputStream os = new ByteArrayOutputStream()
        
        when:
            renderer.doRender(inp, os, cleanHtml)
            
        then:
            thrown org.xhtmlrenderer.util.XRRuntimeException
    }
    
    def "No error thrown on invalid input with cleaning"() {
        setup:
            def renderer = new Html2PdfConverter("")
            def input = "<html><body><p>Test</p></body>"
            def cleanHtml = Boolean.TRUE
            ByteArrayInputStream inp = new ByteArrayInputStream(input.getBytes())
            ByteArrayOutputStream os = new ByteArrayOutputStream()
        
        when:
            renderer.doRender(inp, os, cleanHtml)
            
        then:
            notThrown org.xhtmlrenderer.util.XRRuntimeException
    }
    
    def "Clean exit on valid input without cleaning"() {
        setup:
            def renderer = new Html2PdfConverter("")
            def input = "<html><body><p>Test</p></body></html>"
            def cleanHtml = Boolean.FALSE
            ByteArrayInputStream inp = new ByteArrayInputStream(input.getBytes())
            ByteArrayOutputStream os = new ByteArrayOutputStream()
        
        when:
            renderer.doRender(inp, os, cleanHtml)
            
        then:
            notThrown Exception
    }
    
    def "Clean exit on valid input with cleaning"() {
        setup:
            def renderer = new Html2PdfConverter("")
            def input = "<html><body><p>Test</p></body></html>"
            def cleanHtml = Boolean.TRUE
            ByteArrayInputStream inp = new ByteArrayInputStream(input.getBytes())
            ByteArrayOutputStream os = new ByteArrayOutputStream()
        
        when:
            renderer.doRender(inp, os, cleanHtml)
            
        then:
            notThrown Exception
    }
    
    def "Error thrown on file method"() {
        setup:
            def renderer = new Html2PdfConverter("")
            def inputFile = new File("")
            def outputFile = new File("")
            def cleanHtml = Boolean.TRUE
        
        when:
            renderer.doRender(inputFile, outputFile, cleanHtml)
            
        then:
            thrown RuntimeException
    }
    
}
