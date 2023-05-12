package com.coursework.predatorpreymodel.classes;

public class EmulationParameters {
    private Integer fieldLength, fieldWidth;
    private Integer numberOfPredators, numberOfPreys;
    private Integer numberOfGrass;

    public EmulationParameters(Integer fieldLength, Integer fieldWidth, Integer numberOfPredators, Integer numberOfPreys, Integer numberOfGrass) {
        this.fieldLength = fieldLength;
        this.fieldWidth = fieldWidth;
        this.numberOfPredators = numberOfPredators;
        this.numberOfPreys = numberOfPreys;
        this.numberOfGrass = numberOfGrass;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public Integer getFieldWidth() {
        return fieldWidth;
    }

    public Integer getNumberOfPredators() {
        return numberOfPredators;
    }

    public Integer getNumberOfPreys() {
        return numberOfPreys;
    }

    public Integer getNumberOfGrass() {
        return numberOfGrass;
    }
}