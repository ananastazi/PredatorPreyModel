package com.coursework.predatorpreymodel.classes;

import eu.hansolo.toolbox.tuples.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
        playField.setPlayField(emulationParameters.getFieldHeight(), emulationParameters.getFieldWidth());
        cellsFieldPlacing();
        individualsFieldPlacing();
    }

    void cellsFieldPlacing() {
        UniquePositionGenerator uniquePositionGenerator = new UniquePositionGenerator(emulationParameters.getFieldHeight(), emulationParameters.getFieldWidth());
        Position pos;
        this.grass = new ArrayList<>();
        for (int i = 0; i < emulationParameters.getNumberOfGrass(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            grass.add(new Grass(pos, UniqueIdGenerator.getInstance().getNewUniqueId()));
            playField.getField().get(pos.getX()).get(pos.getY()).setTypeOfCell(1);
            playField.getField().get(pos.getX()).get(pos.getY()).setGrass(grass.get(grass.size() - 1));
            QueueOfChanges.getInstance().add(new Pair<>(0, new Pair<>(null, pos))); // adding change to the queue
        }
    }
    void individualsFieldPlacing() {
        UniquePositionGenerator uniquePositionGenerator = new UniquePositionGenerator(emulationParameters.getFieldHeight(), emulationParameters.getFieldWidth());
        Position pos;
        predators = new ArrayList<>();
        preys = new ArrayList<>();
        for (int i = 0; i < emulationParameters.getNumberOfPredators(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            predators.add(new Predator(UniqueIdGenerator.getInstance().getNewUniqueId(), pos, emulationParameters.getPredatorsEnergy(), emulationParameters.getPredatorsCriticalEnergy(), emulationParameters.getPredatorsLifespan(), emulationParameters.getPredatorsSearchRadius()));
            playField.getField().get(pos.getX()).get(pos.getY()).setIndividual(predators.get(predators.size() - 1)); // adjusting the field
            QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(null, pos))); // adding change to the queue
        }
        for (int i = 0; i < emulationParameters.getNumberOfPreys(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            preys.add(new Prey(UniqueIdGenerator.getInstance().getNewUniqueId(), pos, emulationParameters.getPreysEnergy(), emulationParameters.getPreysCriticalEnergy(), emulationParameters.getPreysLifespan(), emulationParameters.getPreysSearchRadius()));
            playField.getField().get(pos.getX()).get(pos.getY()).setIndividual(preys.get(preys.size() - 1)); // adjusting the field
            QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(null, pos))); // adding change to the queue
        }
    }

    public void performTact() {
        System.out.println("Predators alive: " + getPredatorsAlive());
        System.out.println("Preys alive: " + getPreysAlive());
        System.out.println();
        for (Predator predator : predators) {
            predator.act();
        }
        for (Prey prey : preys) {
            prey.act();
        }
        for (Grass oneGrass : grass) {
            oneGrass.act();
        }
    }

    public Integer getPredatorsAlive() {
        Integer res = 0;
        for (Predator predator : predators) {
            if (predator.state)
                res++;
        }
        return res;
    }

    public Integer getPreysAlive() {
        Integer res = 0;
        for (Prey prey : preys) {
            if (prey.state)
                res++;
        }
        return res;
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
