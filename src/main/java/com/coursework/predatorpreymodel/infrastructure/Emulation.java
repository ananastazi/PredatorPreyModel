package com.coursework.predatorpreymodel.infrastructure;

import com.coursework.predatorpreymodel.utils.UniqueIdGenerator;
import com.coursework.predatorpreymodel.utils.UniquePositionGenerator;
import com.coursework.predatorpreymodel.domain.interfaces.Builder;
import com.coursework.predatorpreymodel.parameters.EmulationParameters;
import com.coursework.predatorpreymodel.domain.cell.typesOfCell.Grass;
import com.coursework.predatorpreymodel.utils.Position;
import com.coursework.predatorpreymodel.domain.individuals.Predator;
import com.coursework.predatorpreymodel.domain.individuals.Prey;
import eu.hansolo.toolbox.tuples.Pair;

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
        QueueOfChanges.getInstance().add(new Pair<>(-1, new Pair<>(null, null))); // creating a null record to differentiate a new tact from the empty queue due to absence of the changes
        for (int i = 0; i < predators.size(); i++) {
            predators.get(i).act();
        }
        for (int i = 0; i < preys.size(); i++) {
            preys.get(i).act();
        }
        for (int i = 0; i < grass.size(); i++) {
            grass.get(i).act();
        }
    }

    // probably for the statistics
    public Integer getPreysAlive() {
        Integer res = 0;
        for (Prey prey : preys) {
            if (prey.getState())
                res++;
        }
        return res;
    }

    // probably for the statistics
    public Integer getPredatorsAlive() {
        Integer res = 0;
        for (Predator predator : predators) {
            if (predator.getState())
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
