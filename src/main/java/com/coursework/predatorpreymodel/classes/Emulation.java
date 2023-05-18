package com.coursework.predatorpreymodel.classes;

import eu.hansolo.toolbox.tuples.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Emulation implements Builder {
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


    @Override
    public void addGrass(Position pos) {
        Grass grass = new Grass(pos, UniqueIdGenerator.getInstance().getNewUniqueId());
        this.grass.add(grass);
        playField.getField().get(pos.getX()).get(pos.getY()).setTypeOfCell(1);
        playField.getField().get(pos.getX()).get(pos.getY()).setGrass(grass);
        QueueOfChanges.getInstance().add(new Pair<>(0, new Pair<>(null, pos))); // adding change to the queue
    }

    @Override
    public void addPredator(Position pos, Integer energy) {
        Predator predator = new Predator(this, UniqueIdGenerator.getInstance().getNewUniqueId(), pos, emulationParameters.getPredatorsCriticalEnergy(), energy, emulationParameters.getPredatorsMaxEnergy(), emulationParameters.getPredatorsLifespan(), emulationParameters.getPredatorsSearchRadius());
        predators.add(predator);
        playField.getField().get(pos.getX()).get(pos.getY()).setIndividual(predator); // adjusting the field
        QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(null, pos))); // adding change to the queue
    }

    @Override
    public void addPrey(Position pos, Integer energy) {
        Prey prey = new Prey(this, UniqueIdGenerator.getInstance().getNewUniqueId(), pos, emulationParameters.getPreysCriticalEnergy(), energy, emulationParameters.getPreysMaxEnergy(), emulationParameters.getPreysLifespan(), emulationParameters.getPreysSearchRadius());
        preys.add(prey);
        playField.getField().get(pos.getX()).get(pos.getY()).setIndividual(prey); // adjusting the field
        QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(null, pos))); // adding change to the queue
    }

    void cellsFieldPlacing() {
        UniquePositionGenerator uniquePositionGenerator = new UniquePositionGenerator(emulationParameters.getFieldHeight(), emulationParameters.getFieldWidth());
        Position pos;
        this.grass = new ArrayList<>();
        for (int i = 0; i < emulationParameters.getNumberOfGrass(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            addGrass(pos);
        }
    }
    void individualsFieldPlacing() {
        UniquePositionGenerator uniquePositionGenerator = new UniquePositionGenerator(emulationParameters.getFieldHeight(), emulationParameters.getFieldWidth());
        Position pos;
        predators = new ArrayList<>();
        preys = new ArrayList<>();
        for (int i = 0; i < emulationParameters.getNumberOfPredators(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            addPredator(pos, emulationParameters.getPredatorsEnergy());
        }
        for (int i = 0; i < emulationParameters.getNumberOfPreys(); ++i) {
            pos = uniquePositionGenerator.GetNewPos();
            addPrey(pos, emulationParameters.getPreysEnergy());
        }
    }

    public void performTact() {
        System.out.println("Predators alive: " + getPredatorsAlive());
        System.out.println("Preys alive: " + getPreysAlive());
        System.out.println();
        QueueOfChanges.getInstance().add(new Pair<>(-1, new Pair<>(null, null))); // creating a null record to differentiate a new tact from the empty queue due to absence of the changes
//        for (Predator predator : predators) {
//            predator.act();
//        }
        if (predators.size() > 0) {
            predators.get(predators.size() - 1).act();
            System.out.println("GOOD");
        }
        else
            System.out.println("STRANGE");
        for (Prey prey : preys) {
            prey.act();
        }
        for (Grass oneGrass : grass) {
            oneGrass.act();
        }
    }

    private Integer getPredatorsAlive() {
        Integer res = 0;
        for (Predator predator : predators) {
            if (predator.state)
                res++;
        }
        return res;
    }

    private Integer getPreysAlive() {
        Integer res = 0;
        for (Prey prey : preys) {
            if (prey.state)
                res++;
        }
        return res;
    }

// three getters are not used !!!!!
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
