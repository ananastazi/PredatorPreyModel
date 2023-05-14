package com.coursework.predatorpreymodel.classes;

import java.util.ArrayList;
import java.util.List;

public class Emulation {
    private PlayField playField = PlayField.getInstance();
    private List<Predator> predators;
    private List<Prey> preys;
    private List<Grass> grass;
    private final EmulationParameters emulationParameters = EmulationParameters.getInstance();

    public Emulation() {
        this.playField.resetPlayField();
        this.predators = null;
        this.preys = null;
        this.grass = null;
    }

    public void prepareEmulation() {
        playField.setPlayField(emulationParameters.getFieldWidth(), emulationParameters.getFieldHeight());
        cellsFieldPlacing();
        individualsFieldPlacing();
    }

    void cellsFieldPlacing() {
        UniquePositionGenerator uniquePositionGenerator = new UniquePositionGenerator(emulationParameters.getFieldHeight(), emulationParameters.getFieldWidth());
        Position pos;
        this.grass = new ArrayList<>();
        for (int i = 0; i < emulationParameters.getNumberOfGrass(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            grass.add(new Grass(pos, i));
            playField.getField().get(pos.getX()).get(pos.getY()).setTypeOfCell(1);
        }
    }
    void individualsFieldPlacing() {
        UniquePositionGenerator uniquePositionGenerator = new UniquePositionGenerator(emulationParameters.getFieldHeight(), emulationParameters.getFieldWidth());
        Position pos;
        predators = new ArrayList<>();
        preys = new ArrayList<>();
        for (int i = 0; i < emulationParameters.getNumberOfPredators(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            predators.add(new Predator(i, pos, emulationParameters.getPredatorsEnergy(), emulationParameters.getPredatorsCriticalEnergy(), emulationParameters.getPredatorsLifespan(), emulationParameters.getPredatorsSearchRadius()));
        }
        for (int i = 0; i < emulationParameters.getNumberOfPreys(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            preys.add(new Prey(i, pos, emulationParameters.getPreysEnergy(), emulationParameters.getPreysCriticalEnergy(), emulationParameters.getPreysLifespan(), emulationParameters.getPreysSearchRadius()));
        }
    }

    void performTact() {

    }

    // region Getters/Setters
    public PlayField getPlayField() {
        return playField;
    }

    public List<Predator> getPredators() {
        return predators;
    }

    public List<Prey> getPreys() {
        return preys;
    }

    public List<Grass> getGrass() {
        return grass;
    }
    // endregion
}
