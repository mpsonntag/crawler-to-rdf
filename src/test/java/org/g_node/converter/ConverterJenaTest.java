/**
 * Copyright (c) 2016, German Neuroinformatics Node (G-Node)
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted under the terms of the BSD License. See
 * LICENSE file in the root of the Project.
 */
package org.g_node.converter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link ConverterJenaTest} class.
 *
 * @author Michael Sonntag (sonntag@bio.lmu.de)
 */
public class ConverterJenaTest {

    private ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private PrintStream stdout;

    private final String tmpRoot = System.getProperty("java.io.tmpdir");
    private final String testFolderName = "ConverterJenaTest";
    private final Path testFileFolder = Paths.get(tmpRoot, testFolderName);

    /**
     * Set up the main logger. Redirect Out stream and create a test folder and the main
     * test file in the java temp directory.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        this.stdout = System.out;
        this.outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.outStream));

        Logger rootLogger = Logger.getRootLogger();
        rootLogger.setLevel(Level.INFO);
        rootLogger.addAppender(
                new ConsoleAppender(
                        new PatternLayout("[%-5p] %m%n")
                )
        );
    }

    /**
     * Reset Out stream to the console and remove all created
     * folders and files after the tests are done.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {

        System.setOut(this.stdout);

        if (Files.exists(this.testFileFolder)) {
            FileUtils.deleteDirectory(this.testFileFolder.toFile());
        }
    }


    @Test
    public void testNonExistingFile() throws Exception {
        final String nonExistingFile = "iDoNotExistAtAll!";
        final String errorMessage = String.join("", "File ", nonExistingFile, " does not exist.");

        ConverterJena.runConverter(nonExistingFile, "", "");
        assertThat(this.outStream.toString()).contains(errorMessage);
    }

    @Test
    public void testNonRdfFile() throws Exception {
        final String testFileName = "test.txt";
        final File textFile = this.testFileFolder.resolve(testFileName).toFile();
        FileUtils.write(textFile, "This is a normal text file.");

        final String errorMessage = String.join("", "Failed to load file '",
                textFile.getAbsolutePath(),"'. Ensure it is a valid RDF file.");

        ConverterJena.runConverter(textFile.getAbsolutePath(), "", "");
        assertThat(this.outStream.toString()).contains(errorMessage);
    }

    @Test
    public void testInvalidRdfFile() throws Exception {
        final String testFileName = "test.ttl";
        final File invalidRdfFile = this.testFileFolder.resolve(testFileName).toFile();
        FileUtils.write(invalidRdfFile, "This is an invalid rdf file!");

        final String errorMessage = "Out of place: [KEYWORD:";

        ConverterJena.runConverter(invalidRdfFile.getAbsolutePath(), "", "");
        assertThat(this.outStream.toString()).contains(errorMessage);
    }

    @Test
    public void testConvertRdfFile() throws Exception {
        final String miniTTL = "@prefix foaf:  <http://xmlns.com/foaf/0.1/> . _:a foaf:name \"TestName\" .\n";
        final File validRdfTestFile = this.testFileFolder.resolve("test.ttl").toFile();
        FileUtils.write(validRdfTestFile, miniTTL);

        final String outputFile = this.testFileFolder.resolve("out.rdf").toString();
        final String outputFormat = "RDF/XML";

        assertThat(Files.exists(Paths.get(outputFile))).isFalse();
        ConverterJena.runConverter(validRdfTestFile.getAbsolutePath(), outputFile, outputFormat);
        assertThat(Files.exists(Paths.get(outputFile))).isTrue();
    }

}
