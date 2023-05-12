package com.coursework.predatorpreymodel.classes;

import java.util.ArrayList;
import java.util.List;

public class Emulation {
    private PlayField playField;
    private List<Predator> predators;
    private List<Prey> preys;
    private List<Grass> grass;

    private EmulationParameters emulationParameters;

    public Emulation(EmulationParameters emulationParameters) {
        this.playField = new PlayField(emulationParameters.getFieldLength(), emulationParameters.getFieldWidth());
        this.predators = new ArrayList<>(emulationParameters.getNumberOfPredators());
        this.preys = new ArrayList<>(emulationParameters.getNumberOfPreys());
        this.grass = new ArrayList<>(emulationParameters.getNumberOfGrass());
    }

    public PlayField getPlayField() {
        return playField;
    }

    public List<Predator> getPredators() {
        return predators;
    }

    public void setPredators(List<Predator> predators) {
        this.predators = predators;
    }

    public List<Prey> getPreys() {
        return preys;
    }

    public void setPreys(List<Prey> preys) {
        this.preys = preys;
    }

    public List<Grass> getGrass() {
        return grass;
    }

    public void setGrass(List<Grass> grass) {
        this.grass = grass;
    }

    void individualsFieldPlacing() {

    }

    void performTact() {

    }
}
