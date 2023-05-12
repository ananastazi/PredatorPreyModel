package com.coursework.predatorpreymodel.classes;

public class EmulationParameters {
    private Integer fieldLength = 10, fieldWidth = 10;
    private Integer lifeTacts = 10;
    private Integer numberOfPredators = 2, numberOfPreys = 2;
    private Integer numberOfGrass;
    private Integer predatorsEnergy, predatorsCriticalEnergy, preysEnergy, preysCriticalEnergy;
    private Integer predatorsLifespan, preysLifespan;

    public EmulationParameters(EmulationParameters emulationParameters, Integer fieldLength, Integer fieldWidth, Integer lifeTacts) {
        if (emulationParameters != null) {
            this.numberOfPredators = emulationParameters.numberOfPredators;
            this.numberOfPreys = emulationParameters.numberOfPreys;
            this.predatorsEnergy = emulationParameters.predatorsEnergy;
            this.predatorsCriticalEnergy = emulationParameters.predatorsCriticalEnergy;
            this.preysEnergy = emulationParameters.preysEnergy;
            this.preysCriticalEnergy = emulationParameters.preysCriticalEnergy;
            this.predatorsLifespan = emulationParameters.predatorsLifespan;
            this.preysLifespan = emulationParameters.preysLifespan;
        }

        this.fieldLength = fieldLength;
        this.fieldWidth = fieldWidth;
        this.lifeTacts = lifeTacts;
        this.numberOfGrass = fieldLength * fieldWidth;
    }
    public EmulationParameters(EmulationParameters emulationParameters, Integer numberOfPredators, Integer numberOfPreys, Integer predatorsEnergy, Integer predatorsCriticalEnergy, Integer preysEnergy, Integer preysCriticalEnergy, Integer predatorsLifespan, Integer preysLifespan) {
        if (emulationParameters != null) {
            this.fieldLength = emulationParameters.fieldLength;
            this.fieldWidth = emulationParameters.fieldWidth;
            this.lifeTacts = emulationParameters.lifeTacts;
            this.numberOfGrass = emulationParameters.numberOfGrass;
        }
        this.numberOfPredators = numberOfPredators;
        this.numberOfPreys = numberOfPreys;
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