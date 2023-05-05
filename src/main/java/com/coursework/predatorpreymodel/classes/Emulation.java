package com.coursework.predatorpreymodel.classes;

import com.classes.Cell;
import com.classes.Grass;
import com.classes.Species;

import java.util.List;

public class Emulation {
    private List<List<com.classes.Cell>> playField;
    private List<com.classes.Species> predators;
    private List<com.classes.Species> preys;
    private List<com.classes.Grass> grass;

    public Emulation(List<List<com.classes.Cell>> playField, List<com.classes.Species> predators, List<com.classes.Species> preys, List<com.classes.Grass> grass) {
        this.playField = playField;
        this.predators = predators;
        this.preys = preys;
        this.grass = grass;
    }

    public List<List<com.classes.Cell>> getPlayField() {
        return playField;
    }

    public void setPlayField(List<List<Cell>> playField) {
        this.playField = playField;
    }

    public List<com.classes.Species> getPredators() {
        return predators;
    }

    public void setPredators(List<com.classes.Species> predators) {
        this.predators = predators;
    }

    public List<com.classes.Species> getPreys() {
        return preys;
    }

    public void setPreys(List<Species> preys) {
        this.preys = preys;
    }

    public List<com.classes.Grass> getGrass() {
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
