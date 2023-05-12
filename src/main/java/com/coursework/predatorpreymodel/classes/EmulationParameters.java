package com.coursework.predatorpreymodel.classes;

public class EmulationParameters {
    private Integer fieldLength, fieldWidth;
    private Integer lifeTacts;
    private Integer numberOfPredators, numberOfPreys;
    private Integer numberOfGrass;
    private Integer predatorsEnergy, predatorsCriticalEnergy, preysEnergy, preysCriticalEnergy;
    private Integer predatorsLifespan, preysLifespan;

    public EmulationParameters(Integer fieldLength, Integer fieldWidth, Integer lifeTacts, Integer numberOfPredators, Integer numberOfPreys, Integer numberOfGrass, Integer predatorsEnergy, Integer predatorsCriticalEnergy, Integer preysEnergy, Integer preysCriticalEnergy, Integer predatorsLifespan, Integer preysLifespan) {
        this.fieldLength = fieldLength;
        this.fieldWidth = fieldWidth;
        this.lifeTacts = lifeTacts;
        this.numberOfPredators = numberOfPredators;
        this.numberOfPreys = numberOfPreys;
        this.numberOfGrass = numberOfGrass;
        this.predatorsEnergy = predatorsEnergy;
        this.predatorsCriticalEnergy = predatorsCriticalEnergy;
        this.preysEnergy = preysEnergy;
        this.preysCriticalEnergy = preysCriticalEnergy;
        this.predatorsLifespan = predatorsLifespan;
        this.preysLifespan = preysLifespan;
    }

    public Integer getFieldLength() {
        return fieldLength;
    }

    public Integer getFieldWidth() {
        return fieldWidth;
    }

    public Integer getLifeTacts() {
        return lifeTacts;
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

    public Integer getPredatorsEnergy() {
        return predatorsEnergy;
    }

    public Integer getPredatorsCriticalEnergy() {
        return predatorsCriticalEnergy;
    }

    public Integer getPreysEnergy() {
        return preysEnergy;
    }

    public Integer getPreysCriticalEnergy() {
        return preysCriticalEnergy;
    }

    public Integer getPredatorsLifespan() {
        return predatorsLifespan;
    }

    public Integer getPreysLifespan() {
        return preysLifespan;
    }
}