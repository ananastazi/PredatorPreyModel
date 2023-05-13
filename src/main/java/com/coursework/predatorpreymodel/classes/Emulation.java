package com.coursework.predatorpreymodel.classes;

import com.coursework.predatorpreymodel.UniquePositionGenerator;

import java.util.ArrayList;
import java.util.List;

public class Emulation {
    private PlayField playField;
    private List<Predator> predators;
    private List<Prey> preys;
    private List<Grass> grass;
    private EmulationParameters emulationParameters;

    public Emulation(EmulationParameters emulationParameters) {
        this.emulationParameters = emulationParameters;
        this.playField = PlayField.getInstance();
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

    public void prepareEmulation() {
        playField.setPlayField(emulationParameters.getFieldWidth(), emulationParameters.getFieldHeight());
        cellsFieldPlacing();
        individualsFieldPlacing();
    }

    void cellsFieldPlacing() {
        for (int i = 0; i < playField.field.size(); ++i) {
            for (int j = 0; j < playField.field.get(0).size(); j++) {
                Integer id = i * playField.field.size() + j;
                grass.add(new Grass(new Position(i, j), id));
                playField.field.get(i).get(j).setTypeOfCell(1);
            }
        }
    }
    void individualsFieldPlacing() {
        UniquePositionGenerator uniquePositionGenerator = new UniquePositionGenerator(emulationParameters.getFieldHeight(), emulationParameters.getFieldWidth());
        Position pos;
        for (int i = 0; i < emulationParameters.getNumberOfPredators(); ++i) {
            pos = uniquePositionGenerator.GetPos();
            predators.add(new Predator(i, pos, emulationParameters.getPredatorsEnergy(), emulationParameters.getPredatorsCriticalEnergy(), emulationParameters.getPredatorsLifespan(), emulationParameters.getPredatorsSearchRadius()));
        }
        for (int i = 0; i < emulationParameters.getNumberOfPreys(); ++i) {
            pos = uniquePositionGenerator.GetPos();
            preys.add(new Prey(i, pos, emulationParameters.getPreysEnergy(), emulationParameters.getPreysCriticalEnergy(), emulationParameters.getPreysLifespan(), emulationParameters.getPreysSearchRadius()));
        }
    }

    void performTact() {

    }
}
