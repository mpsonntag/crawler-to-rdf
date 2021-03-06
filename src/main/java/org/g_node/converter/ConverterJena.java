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

import com.hp.hpl.jena.rdf.model.Model;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.jena.riot.RiotException;
import org.apache.log4j.Logger;
import org.g_node.micro.rdf.RdfFileServiceJena;

/**
 * Class opening an RDF file and saving the resulting model to another RDF format.
 *
 * @author Michael Sonntag (sonntag@bio.lmu.de)
 */
public final class ConverterJena {
    /**
     * Access to the main LOGGER.
     */
    private static final Logger LOGGER = Logger.getLogger(ConverterJena.class.getName());

    /**
     * Method to convert data stored in one RDF format to another RDF format and save the results to a new file.
     * @param inputFile File containing RDF data.
     * @param outputFile File to which the RDF data will be written.
     * @param outputFormat RDF format of the output file.
     */
    public static void runConverter(final String inputFile, final String outputFile, final String outputFormat) {

        ConverterJena.LOGGER.info("Reading input file...");
        if (!Files.exists(Paths.get(inputFile))) {
            ConverterJena.LOGGER.error(String.join("", "File ", inputFile, " does not exist."));
            return;
        } else if (!RdfFileServiceJena.isValidRdfFile(inputFile)) {
            return;
        }

        Model convertData;

        try {
            convertData = RdfFileServiceJena.openModelFromFile(inputFile);
        } catch (RiotException e) {
            ConverterJena.LOGGER.error(e.getMessage());
            e.printStackTrace();
            return;
        }

        RdfFileServiceJena.saveModelToFile(outputFile, convertData, outputFormat);
    }

}
