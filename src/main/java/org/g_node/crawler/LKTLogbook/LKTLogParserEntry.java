/**
 * Copyright (c) 2015, German Neuroinformatics Node (G-Node)
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted under the terms of the BSD License. See
 * LICENSE file in the root of the Project.
 */

package org.g_node.crawler.LKTLogbook;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Object containing all information parsed from the individual data rows of an ODS sheet.
 *
 * @author Michael Sonntag (sonntag@bio.lmu.de)
 */
public final class LKTLogParserEntry {

    /**
     * Pattern that all DateTime values have to be formatted in
     * to be accepted by this parser.
     */
    private static final String SUPPORTED_DATE_TIME_PATTERN = "dd.MM.yyyy HH:mm";
    /**
     * Formatter used to test DateTime values
     * for the pattern {@link #SUPPORTED_DATE_TIME_PATTERN}.
     */
    private final DateTimeFormatter supportedDateTime = DateTimeFormatter.ofPattern(
            LKTLogParserEntry.SUPPORTED_DATE_TIME_PATTERN
    );
    /**
     * String identifier, short description of the project
     * the current entry belongs to. This value
     * is required.
     */
    private String project;
    /**
     * String identifier, short description of the experiment
     * the current entry belongs to. This value
     * is required.
     */
    private String experiment;
    /**
     * String identifier, short description of the paradigm
     * that is being tested at the current entry.
     */
    private String paradigm;
    /**
     * Specification of the paradigm that is being tested
     * at the current entry.
     */
    private String paradigmSpecifics;
    /**
     * Date and time when the experiment the current entry
     * represents took place. This value
     * is required.
     */
    private LocalDateTime experimentDate;
    /**
     * Name of the experimenter. This value is required.
     */
    private String experimenterName;
    /**
     * Comment about the experiment.
     */
    private String commentExperiment;
    /**
     * Comment about the animal.
     */
    private String commentSubject;
    /**
     * Description of the food the animal received
     * at the time of the current entry.
     */
    private String feed;
    /**
     * Boolean value if the animal is on a diet
     * at the time of the current entry.
     */
    private boolean isOnDiet;
    /**
     * Boolean value if the weight at the current entry
     * is used to calculate weight changes for later
     * entries.
     */
    private boolean isInitialWeight;
    /**
     * Weight of the animal at the time of the current entry.
     */
    private Float weight;
    /**
     * Boolean value if the current entry should
     * be treated as an empty line and therefore
     * not be imported.
     */
    private boolean isEmptyLine = true;

    /**
     * Return the project identifier string.
     * @return See description.
     */
    public String getProject() {
        return this.project;
    }

    /**
     * Set the project identifier string. This entry is one of the required
     * fields to uniquely identify an entry. If the project identifier
     * is not available, the current line cannot be imported, the value of
     * the variable isEmptyLine is set to false.
     * @param prj String containing the project identifier.
     */
    public void setProject(final String prj) {
        if (prj != null && !prj.isEmpty()) {
            this.setIsEmptyLine(false);
        }
        this.project = prj;
    }

    /**
     * Return the experiment identifier string.
     * @return See description.
     */
    public String getExperiment() {
        return this.experiment;
    }

    /**
     * Set the experiment identifier. This entry is one of the required
     * fields to uniquely identify an entry. If the experiment identifier
     * is not available, the current line cannot be imported, the value of
     * the variable isEmptyLine is set to false.
     * @param exp String containing the experiment identifier.
     */
    public void setExperiment(final String exp) {
        if (exp != null && !exp.isEmpty()) {
            this.setIsEmptyLine(false);
        }
        this.experiment = exp;
    }

    /**
     * Return the paradigm for the current entry.
     * @return See description.
     */
    public String getParadigm() {
        return this.paradigm;
    }

    /**
     * Set the paradigm for the current entry.
     * @param para String containing the paradigm for the current entry.
     */
    public void setParadigm(final String para) {
        this.paradigm = para;
    }

    /**
     * Return information about the paradigm.
     * @return See description.
     */
    public String getParadigmSpecifics() {
        return this.paradigmSpecifics;
    }

    /**
     * Set specific information about the paradigm.
     * @param pspec String containing information about the paradigm.
     */
    public void setParadigmSpecifics(final String pspec) {
        this.paradigmSpecifics = pspec;
    }

    /**
     * Return the date of the experiment using format {@link #SUPPORTED_DATE_TIME_PATTERN}.
     * @return See description.
     */
    public LocalDateTime getExperimentDate() {
        return this.experimentDate;
    }

    /**
     * Set the date of the experiment. Check that the date format is conform
     * to {@link #SUPPORTED_DATE_TIME_PATTERN}. This entry is one of the required
     * fields to uniquely identify an entry. If the experiment date
     * is not available, the current line cannot be imported, the value of
     * the variable isEmptyLine is set to false.
     * @param expdt String containing the date of the experiment.
     * @return An error message, if the date was not conform to
     *  {@link #SUPPORTED_DATE_TIME_PATTERN}
     */
    public String setExperimentDate(final String expdt) {
        String errMsg = "";
        if (expdt != null && !expdt.isEmpty()) {
            this.setIsEmptyLine(false);
        }
        try {
            this.experimentDate = LocalDateTime.parse(expdt, this.supportedDateTime);
        } catch (final DateTimeParseException err) {
            if (expdt != null && !expdt.isEmpty()) {
                errMsg = String.join(
                        "", "Invalid experiment date format (", expdt,
                        "). Please check the date and use format '", LKTLogParserEntry.SUPPORTED_DATE_TIME_PATTERN, "'"
                );
            }
        }
        return errMsg;
    }

    /**
     * Return the name of the experimenter.
     * @return See description.
     */
    public String getExperimenterName() {
        return this.experimenterName;
    }

    /**
     * Set the name of the experimenter.
     * @param exn Name of the experimenter.
     */
    public void setExperimenterName(final String exn) {
        if (exn != null && !exn.isEmpty()) {
            this.setIsEmptyLine(false);
        }
        this.experimenterName = exn;
    }

    /**
     * Return comments about the experiment.
     * @return See description.
     */
    public String getCommentExperiment() {
        return this.commentExperiment;
    }

    /**
     * Set comments about the experiment.
     * @param cmtexp Comment about the experiment.
     */
    public void setCommentExperiment(final String cmtexp) {
        this.commentExperiment = cmtexp;
    }

    /**
     * Return comments about the animal at the current entry.
     * @return See description.
     */
    public String getCommentSubject() {
        return this.commentSubject;
    }

    /**
     * Set comments about the animal.
     * @param cmtan Comment about the animal.
     */
    public void setCommentSubject(final String cmtan) {
        this.commentSubject = cmtan;
    }

    /**
     * Return animal feed information.
     * @return See description.
     */
    public String getFeed() {
        return this.feed;
    }

    /**
     * Set the feed for the current entry.
     * @param onfd Information about animal feed.
     */
    public void setFeed(final String onfd) {
        this.feed = onfd;
    }

    /**
     * Return a boolean value if the animal is on diet.
     * @return See description.
     */
    public Boolean getIsOnDiet() {
        return this.isOnDiet;
    }

    /**
     * Set if the animal is on diet at the current entry.
     * @param isod Values y or n which are translated to true or false.
     */
    public void setIsOnDiet(final String isod) {
        this.isOnDiet = isod != null && "y".equals(isod);
    }

    /**
     * Return a boolean value if the current entry contains the
     * initial weight for diet calculations.
     * @return See description.
     */
    public Boolean getIsInitialWeight() {
        return this.isInitialWeight;
    }

    /**
     * Set if the current entry is the initial weight for diet calculations.
     * @param isInWeight Values y or n which are translated to true or false.
     */
    public void setIsInitialWeight(final String isInWeight) {
        this.isInitialWeight = isInWeight != null && "y".equals(isInWeight);
    }

    /**
     * Return the animal weight of the current entry.
     * @return See description.
     */
    public Float getWeight() {
        return this.weight;
    }

    /**
     * Set animal weight for the current entry.
     * @param wght Animal weight.
     * @return Emtpy string if all is well, error message if the conversion failed.
     */
    // TODO Everything in here is ugly as sin, sorry...
    // TODO Refactor parser errors as well as the handling of the decimal place conversion.
    public String setWeight(final String wght) {

        String msg = "";

        if (wght != null && !"".equals(wght)) {
            try {
                final String normWght = wght.replace(",", ".");
                this.weight = Float.parseFloat(normWght);
            } catch (NumberFormatException e) {
                msg = String.join("", "Invalid weight: ", wght);
            }
        }

        return msg;
    }

    /**
     * Return boolean value if the current entry contains no values.
     * @return See description.
     */
    public boolean getIsEmptyLine() {
        return this.isEmptyLine;
    }

    /**
     * Set if the current entry contains no values or does not contain all
     * required fields.
     * Required to check, if an entry is actually empty, but parsed due to
     * existing column format.
     * If any of the required fields project, experiment, experimentDate or
     * experimenterName are not empty, then the line does not qualify as an empty
     * line any longer and has to be dealt with. If all of the required fields
     * are missing, the entry will simply not be imported.
     * TODO check if the description above is actually still completely true.
     * @param iseline If true, the current entry contains no values.
     */
    public void setIsEmptyLine(final boolean iseline) {
        this.isEmptyLine = iseline;
    }

    /**
     * Method to check if a data row from a sheet contains all required entries.
     * @return Empty String or a Message containing all missing entries
     */
    public String isValidEntry() {

        // TODO maybe solve this better by using optional if there is no entry missing?
        String msg = "";

        if (this.getProject() == null || this.getProject().isEmpty()) {
            msg = msg.concat(" Project ");
        }
        if (this.getExperiment() == null || this.getExperiment().isEmpty()) {
            msg = msg.concat(" Experiment ");
        }
        if (this.getExperimentDate() == null) {
            msg = msg.concat(" Experiment date ");
        }
        if (this.getExperimenterName() == null || this.getExperimenterName().isEmpty()) {
            msg = msg.concat(" Name of experimenter ");
        }

        return msg;
    }
}
