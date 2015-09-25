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
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the {@link FileService} class. Output and Error streams are redirected
 * from the console to a different PrintStream and reset after tests are finished
 * to avoid mixing tool error messages with actual test error messages.
 *
 * @author Michael Sonntag (sonntag@bio.lmu.de)
 */
public class FileServiceTest {

    private final String tmpRoot = System.getProperty("java.io.tmpdir");
    private final String testFolderName = "fileservicetest";
    private final String testFileName = "test.txt";
    private final Path testFileFolder = Paths.get(tmpRoot, testFolderName);
    private PrintStream stdout;
    private PrintStream stderr;

    /**
     * Redirect Error and Out stream and create a testfolder and the main
     * test file in the java temp directory.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        final ByteArrayOutputStream errStream = new ByteArrayOutputStream();

        this.stdout = System.out;
        this.stderr = System.err;

        System.setOut(new PrintStream(outStream));
        System.setErr(new PrintStream(errStream));

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
     * Check, that the method returns false when presented with a
     * String referencing a non existing file and returns true if it is
     * presented with String referencing an existing file.
     * @throws Exception
     */
    @Test
    public void testCheckFile() throws Exception {
        final String testNonExistingFilePath =
                this.testFileFolder
                        .resolve("IdoNotExist")
                        .toAbsolutePath().normalize().toString();

        assertThat(FileService.checkFile(testNonExistingFilePath)).isFalse();

        final String testExistingFilePath =
                this.testFileFolder
                        .resolve(this.testFileName)
                        .toAbsolutePath().normalize().toString();

        assertThat(FileService.checkFile(testExistingFilePath)).isTrue();
    }

    /**
     * Check, that a file without a file type or a file type that
     * is not supported returns false and test that a file with a supported
     * file type returns true. This test creates two additional files
     * @throws Exception
     */
    @Test
    public void testCheckFileType() throws Exception {
        final String testFileType = "test";
        final String testFileTypeExt = "test.tex";

        final File currTestFileType = this.testFileFolder.resolve(testFileType).toFile();
        final File currTestFileTypeExt = this.testFileFolder.resolve(testFileTypeExt).toFile();

        FileUtils.write(currTestFileType, "This is a normal test file");
        FileUtils.write(currTestFileTypeExt, "This is a normal test file");

        final List<String> testFileTypes = Collections.singletonList("TXT");

        assertThat(
            FileService.checkFileType(
                    this.testFileFolder
                            .resolve(testFileType)
                            .toAbsolutePath().normalize().toString(),
                    testFileTypes)
            ).isFalse();

        assertThat(
            FileService.checkFileType(
                    this.testFileFolder
                            .resolve(testFileTypeExt)
                            .toAbsolutePath().normalize().toString(),
                    testFileTypes)
        ).isFalse();

        assertThat(
            FileService.checkFileType(
                    this.testFileFolder
                            .resolve(this.testFileName)
                            .toAbsolutePath().normalize().toString(),
                    testFileTypes)
        ).isTrue();

    }

}