package com.coursework.predatorpreymodel.classes;

import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Species {
    protected Integer id;
    protected Position pos;
    protected Position target;
    protected Integer typeOfTarget; // 0 - prey; 1 - prey/partner; 2 - partner;
    protected ArrayList<Position> workaround;
    protected Integer criticalEnergy;
    protected Integer energy;
    protected Integer maxEnergy;
    protected Integer amountOfEnergyForReproduction;
    protected Integer amountOfEnergyForMovement;
    protected Integer lifetimeTacts;
    protected Integer state;
    protected Integer currentAge;
    protected Integer causeOfdeath; // cause of death: 0 - from age, 1 - from hunger, 2 - eaten
    protected Integer searchRadius;

    public Species(Integer id, Position pos, Integer energy, Integer criticalEnergy, Integer lifetimeTacts, Integer searchRadius) {
        this.id = id;
        this.pos = pos;
        this.criticalEnergy = criticalEnergy;
        this.energy = energy;
        this.lifetimeTacts = lifetimeTacts;
        this.searchRadius = searchRadius;
    }

    // region Getters/Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Position getTarget() {
        return target;
    }

    public void setTarget(Position target) {
        this.target = target;
    }

    public Integer getTypeOfTarget() {
        return typeOfTarget;
    }

    public void setTypeOfTarget(Integer typeOfTarget) {
        this.typeOfTarget = typeOfTarget;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getCriticalEnergy() {
        return criticalEnergy;
    }

    public void setCriticalEnergy(Integer criticalEnergy) {
        this.criticalEnergy = criticalEnergy;
    }

    public Integer getAmountOfEnergyForReproduction() {
        return amountOfEnergyForReproduction;
    }

    public void setAmountOfEnergyForReproduction(Integer amountOfEnergyForReproduction) {
        this.amountOfEnergyForReproduction = amountOfEnergyForReproduction;
    }

    public Integer getLifetimeTacts() {
        return lifetimeTacts;
    }

    public void setLifetimeTacts(Integer lifetimeTacts) {
        this.lifetimeTacts = lifetimeTacts;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCurrentAge() {
        return currentAge;
    }

    public void setCurrentAge(Integer currentAge) {
        this.currentAge = currentAge;
    }

    public Integer getMaxEnergy() {
        return maxEnergy;
    }

    public void setMaxEnergy(Integer maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public ArrayList<Position> getWorkaround() {
        return workaround;
    }

    public Integer getCauseOfdeath() {
        return causeOfdeath;
    }
    // endregion

    public void act() {
        if (this.currentAge >= this.lifetimeTacts) {
            this.dieFrom(0);
        }
        else if (this.energy < this.criticalEnergy) {
            this.dieFrom(1);
        }
        else if (this.energy < this.amountOfEnergyForReproduction) {
            this.typeOfTarget = 0;
        }
        else if (this.energy < this.maxEnergy) {
            this.typeOfTarget = 1;
        }
        else {
            this.typeOfTarget = 2;
        }

        this.searchTarget();
    }

    public abstract void move();

    public abstract void doStep();

    public void searchTarget() {
        PlayField playField = PlayField.getInstance();
        Integer[][] localMatrix = new Integer[playField.getHeight()][playField.getWidth()];

        for (int i = 0; i < playField.getHeight(); i++) {
            for (int j = 0; j < playField.getWidth(); j++) {
                localMatrix[i][j] = -1;
            }
        }

        Queue<Position> queue = new LinkedList<>();

        localMatrix[this.pos.getX()][this.pos.getY()] = 0;
        queue.add(new Position(this.pos.getX(), this.pos.getY()));

        int rowNum[] = {-1, 0, 0, 1};
        int colNum[] = {0, -1, 1, 0};

        Boolean isTargetFound = false;
        Position visitCell =  new Position(-1, -1);

        for (int  i = 0; i < this.searchRadius && !isTargetFound; ++i) {
            int qSize = queue.size();
            for (int k = 0; !queue.isEmpty() && k < qSize && !isTargetFound; ++k) {
                for (int j = 0; j < rowNum.length && !isTargetFound; j++) {
                    visitCell = new Position(queue.element().getX() + rowNum[j], queue.element().getY() + colNum[j]);
                    if (localMatrix[visitCell.getX()][visitCell.getY()] == -1) {
                        switch(CheckCell(visitCell, i)) {
                            case -1:
                                localMatrix[visitCell.getX()][visitCell.getY()] = -2;
                                break;
                            case 0:
                                queue.add(visitCell);
                                localMatrix[visitCell.getX()][visitCell.getY()] = i + 1;
                                break;
                            case 1:
                                isTargetFound = true;
                                localMatrix[visitCell.getX()][visitCell.getY()] = i + 1;
                                break;
                        }
                    }
                }
                queue.remove();
            }
        }

        workaround = new ArrayList<>();
        for (int i = localMatrix[visitCell.getX()][visitCell.getY()]; i > 0; --i) {
            for (int j = 0; j < rowNum.length; j++) {
                if (localMatrix[workaround.get(workaround.size() - 1).getX() + rowNum[j]][workaround.get(workaround.size() - 1).getY() + colNum[j]] == (i - 1)) {
                    workaround.add(new Position(i + rowNum[j], j + colNum[j]));
                    break;
                }
            }
        }
    }
    protected abstract Integer CheckCell(Position pos, Integer distance);

    public void createOffspring() {

    }

    public void dieFrom(Integer causeOfdeath) {
        this.causeOfdeath = causeOfdeath;
        this.state = 0;
    }
}
