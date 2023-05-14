package com.coursework.predatorpreymodel.classes;

import javafx.geometry.Pos;

public abstract class Species {
    protected Integer id;
    protected Position pos;
    // IS IT POSITION OR POSITION + ID ?????????????
    protected Position target;
    protected Integer typeOfTarget;
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

    public abstract void searchTarget();

    public void createOffspring() {

    }

    public void dieFrom(Integer causeOfdeath) {
        this.causeOfdeath = causeOfdeath;
        this.state = 0;
    }
}
