/**
 * Copyright (c) 2015, German Neuroinformatics Node (G-Node)
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted under the terms of the BSD License. See
 * LICENSE file in the root of the Project.
 */

package org.g_node.srv;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link RDFService} class. Output and Error streams are redirected
 * from the console to a different PrintStream and reset after tests are finished
 * to avoid mixing tool error messages with actual test error messages.
 *
 * @author Michael Sonntag (sonntag@bio.lmu.de)
 */
public class RDFServiceTest {

    private final String tmpRoot = System.getProperty("java.io.tmpdir");
    private final String testFolderName = "tdfservicetest";
    private final String testFileName = "test.txt";
    private final Path testFileFolder = Paths.get(tmpRoot, testFolderName);

    private ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    private ByteArrayOutputStream errStream = new ByteArrayOutputStream();
    private PrintStream stdout;
    private PrintStream stderr;

    /**
     * Redirect Error and Out stream and create a testfolder and the main
     * test file in the java temp directory.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        this.stdout = System.out;
        this.stderr = System.err;

        this.outStream = new ByteArrayOutputStream();
        this.errStream = new ByteArrayOutputStream();

        System.setOut(new PrintStream(this.outStream));
        System.setErr(new PrintStream(this.errStream));

        final File currTestFile = this.testFileFolder.resolve(this.testFileName).toFile();
        FileUtils.write(currTestFile, "This is a normal test file");

    }

    /**
     * Reset Error and Out stream to the console and remove all created
     * folders and files after the tests are done.
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {

        System.setOut(this.stdout);
        System.setErr(this.stderr);

        if (Files.exists(this.testFileFolder)) {
            FileUtils.deleteDirectory(this.testFileFolder.toFile());
        }
    }

    /**
     * Check, that a Jena RDF model is written to file.
     * @throws Exception
     */
    @Test
    public void testWriteModelToFile() throws Exception {
        final Model model = ModelFactory.createDefaultModel();
        final Resource modelEntry = model.createResource();

        final String outFileName = "testrdf.ttl";
        final String outFilePath = this.testFileFolder.resolve(outFileName).toString();
        final String outFileFormat = "TTL";
        final String testString = String.join(
                "", "[Info] Writing data to RDF file, ",
                outFilePath, " using format '", outFileFormat, "'"
        );

        RDFService.writeModelToFile(outFilePath, model, outFileFormat);

        assertThat(this.outStream.toString()).startsWith(testString);
    }
}