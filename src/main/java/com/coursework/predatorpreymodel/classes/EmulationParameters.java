package com.coursework.predatorpreymodel.classes;

import eu.hansolo.fx.countries.tools.CRegion;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class EmulationParameters {
    private static EmulationParameters single_instance = null;
    private Integer fieldHeight, fieldWidth;
    private Integer lifeTacts;
    private Integer numberOfGrass;
    private Integer numberOfIndividuals;
    private Integer numberOfPredators, numberOfPreys;
    private Integer predatorsCriticalEnergy, preysCriticalEnergy;
    private Integer predatorsEnergy, preysEnergy;
    private Integer predatorsMaxEnergy, preysMaxEnergy;
    private Integer predatorsLifespan, preysLifespan;
    private Integer predatorsSearchRadius = 6, preysSearchRadius = 4;
    private EmulationParameters() {}
    public static synchronized EmulationParameters getInstance() {
        if (single_instance == null)
            single_instance = new EmulationParameters();
        return single_instance;
    }

    public void setEmulationParametersField(String fieldHeight, String fieldWidth, String numberOfIndividuals, String lifeTacts) {
        this.fieldWidth = setNumber(fieldWidth, 10);
        this.fieldHeight = setNumber(fieldHeight, 10);
        this.numberOfIndividuals = setNumber(numberOfIndividuals, this.fieldHeight * this.fieldWidth / 6);
        this.lifeTacts = setNumber(lifeTacts, 10);
        this.numberOfGrass = this.fieldHeight * this.fieldWidth;
    }
    public void setEmulationParametersSpecies(String numberOfPredators, String predatorsMaxEnergy, String predatorsCriticalEnergy, String predatorsLifespan, String numberOfPreys, String preysMaxEnergy, String preysCriticalEnergy, String preysLifespan) {
        this.numberOfPredators = setNumber(numberOfPredators, numberOfIndividuals / 4);
        this.predatorsCriticalEnergy = setNumber(predatorsCriticalEnergy, 2);
        this.predatorsMaxEnergy = setNumber(predatorsMaxEnergy, this.predatorsCriticalEnergy + 10);
        this.predatorsEnergy = this.predatorsCriticalEnergy + min(10, (this.predatorsMaxEnergy - this.predatorsCriticalEnergy) / 2);
        this.predatorsLifespan = setNumber(predatorsLifespan, 7);
        this.numberOfPreys = setNumber(numberOfPreys, max(this.numberOfPredators * 2, numberOfIndividuals / 2));
        this.preysCriticalEnergy = setNumber(preysCriticalEnergy, 2);
        this.preysMaxEnergy = setNumber(preysMaxEnergy, this.preysCriticalEnergy + 10);
        this.preysEnergy = this.preysCriticalEnergy + min(5, (this.preysMaxEnergy - this.preysCriticalEnergy) / 2);
        this.preysLifespan = setNumber(preysLifespan, 5);
    }

    private Integer setNumber(String strNumber, Integer standardValue) {
        if (strNumber != null && strNumber.length() != 0)
            return Integer.parseInt(strNumber);
        return standardValue;
    }

    // region Getters
    public Integer getFieldHeight() {
        return fieldHeight;
    }

    public Integer getFieldWidth() {
        return fieldWidth;
    }

    public Integer getLifeTacts() {
        return lifeTacts;
    }

    public Integer getNumberOfIndividuals() {
        return numberOfIndividuals;
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

    public Integer getPredatorsCriticalEnergy() {
        return predatorsCriticalEnergy;
    }

    public Integer getPredatorsEnergy() {
        return predatorsEnergy;
    }

    public Integer getPredatorsMaxEnergy() {
        return predatorsMaxEnergy;
    }

    public Integer getPreysCriticalEnergy() {
        return preysCriticalEnergy;
    }

    public Integer getPreysEnergy() {
        return preysEnergy;
    }

    public Integer getPreysMaxEnergy() {
        return preysMaxEnergy;
    }

    public Integer getPredatorsLifespan() {
        return predatorsLifespan;
    }

    public Integer getPreysLifespan() {
        return preysLifespan;
    }

    public Integer getPredatorsSearchRadius() {
        return predatorsSearchRadius;
    }

    public Integer getPreysSearchRadius() {
        return preysSearchRadius;
    }
    // endregion
}