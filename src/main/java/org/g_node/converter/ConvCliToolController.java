/**
 * Copyright (c) 2015, German Neuroinformatics Node (G-Node)
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted under the terms of the BSD License. See
 * LICENSE file in the root of the Project.
 */

package org.g_node.converter;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.g_node.micro.commons.CliToolController;
import org.g_node.micro.rdf.RdfFileServiceJena;
import org.g_node.srv.CliOptionService;
import org.g_node.srv.CtrlCheckService;

/**
 * Controller class for the RDF to RDF converter.
 *
 * @author Michael Sonntag (sonntag@bio.lmu.de)
 */
public class ConvCliToolController implements CliToolController {
    /**
     * Method returning the commandline options of the RDF to RDF converter.
     * @return Available commandline options.
     */
    public final Options options() {
        final Options options = new Options();

        final Option opHelp = CliOptionService.getHelpOpt("");
        final Option opIn = CliOptionService.getInFileOpt(
                "Input RDF file that's supposed to be converted into a different RDF format.");
        final Option opOut = CliOptionService.getOutFileOpt("");
        final Option opFormat = CliOptionService.getOutFormatOpt("", RdfFileServiceJena.RDF_FORMAT_MAP.keySet());

        options.addOption(opHelp);
        options.addOption(opIn);
        options.addOption(opOut);
        options.addOption(opFormat);

        return options;
    }

    /**
     * Method converting data from an input RDF file to an RDF file of a different supported RDF format.
     * @param cmd User provided {@link CommandLine} input.
     */
    public final void run(final CommandLine cmd) {

        final Map<String, String> rdfFormatExtensions = RdfFileServiceJena.RDF_FORMAT_EXTENSION;
        final Set<String> rdfFormatKeys = RdfFileServiceJena.RDF_FORMAT_MAP.keySet();

        final String inputFile = cmd.getOptionValue("i");
        if (!CtrlCheckService.isExistingFile(inputFile)) {
            return;
        }

        final Set<String> checkExtension = rdfFormatExtensions.values()
                .stream()
                .map(c->c.toUpperCase(Locale.ENGLISH))
                .collect(Collectors.toSet());
        if (!CtrlCheckService.isSupportedInFileType(inputFile, checkExtension)) {
            return;
        }

        if (!RdfFileServiceJena.isValidRdfFile(inputFile)) {
            return;
        }

        final String outputFormat = cmd.getOptionValue("f", "TTL").toUpperCase(Locale.ENGLISH);
        if (!CtrlCheckService.isSupportedOutputFormat(outputFormat, rdfFormatKeys)) {
            return;
        }

        final int i = inputFile.lastIndexOf('.');
        final String defaultOutputFile = String.join("", inputFile.substring(0, i), "_out");
        String outputFile = cmd.getOptionValue("o", defaultOutputFile);

        if (!outputFile.toLowerCase().endsWith(rdfFormatExtensions.get(outputFormat))) {
            outputFile = String.join("", outputFile, ".", rdfFormatExtensions.get(outputFormat));
        }

        ConverterJena.runConverter(inputFile, outputFile, outputFormat);
    }

}
