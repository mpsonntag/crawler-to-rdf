// Copyright (c) 2015, German Neuroinformatics Node (G-Node)
//
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted under the terms of the BSD License. See
// LICENSE file in the root of the Project.

package org.g_node.crawler.LKTLogbook;

/**
 * Object containing all information parsed from the individual data rows of an ODS sheet
 */
public class LKTLogbookEntry {

    private String project;
    private String experiment;
    private String paradigm;
    private String experimentDate;
    private String experimenter;
    private String firstName;
    private String lastName;
    private String commentExperiment;
    private String commentAnimal;
    private String feed;
    private String diet;
    private String initialWeight;
    private String weight;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getExperiment() {
        return experiment;
    }

    public void setExperiment(String experiment) {
        this.experiment = experiment;
    }

    public String getParadigm() {
        return paradigm;
    }

    public void setParadigm(String paradigm) {
        this.paradigm = paradigm;
    }

    public String getExperimentDate() {
        return experimentDate;
    }

    public void setExperimentDate(String experimentDate) {
        this.experimentDate = experimentDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCommentExperiment() {
        return commentExperiment;
    }

    public void setCommentExperiment(String commentExperiment) {
        this.commentExperiment = commentExperiment;
    }

    public String getCommentAnimal() {
        return commentAnimal;
    }

    public void setCommentAnimal(String commentAnimal) {
        this.commentAnimal = commentAnimal;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(String initialWeight) {
        this.initialWeight = initialWeight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public boolean checkValidEntry() {
        boolean isValid = true;

        if(getProject() == null || getProject().equals("")){
            isValid = false;
        }
        if(getExperiment() == null || getExperiment().equals("")){
            isValid = false;
        }
        if(getExperimentDate() == null || getExperimentDate().equals("")){
            isValid = false;
        }

        return isValid;
    }

    public String getExperimenter() {
        return experimenter;
    }

    public void setExperimenter(String experimenter) {
        this.experimenter = experimenter;
    }

    public String getFeed() {
        return feed;
    }

    public void setFeed(String feed) {
        this.feed = feed;
    }
}
