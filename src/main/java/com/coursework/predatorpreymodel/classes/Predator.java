package com.coursework.predatorpreymodel.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Predator extends Species {
    public Predator(Integer id, Position pos, Integer energy, Integer criticalEnergy, Integer lifetimeTacts, Integer searchRadius) {
        super(id, pos, energy, criticalEnergy, lifetimeTacts, searchRadius);
    }

    @Override
    public  void move() {


    }

    @Override
    public void doStep() {
        this.move();

        if (this.pos == this.target) {
            if (this.typeOfTarget == 0) {
                this.eatPrey();
            } else {
                this.createOffspring();
            }
        }
    }

    @Override
    // -1 - filled; 0 - empty; 1 - target;
    protected Integer CheckCell(Position pos, Integer distance) {
        if (this.lifetimeTacts - this.currentAge < (distance + 1) / 2)
            return -1;
        // if filled, is it enough energy, age to get to
        // checking typeOfTarget too
        PlayField playField = PlayField.getInstance();
        Species individual = playField.getField().get(pos.getX()).get(pos.getY()).getIndividual();
        if (individual == null)
            return 0;

        if (typeOfTarget == 0 || typeOfTarget == 1) {
            if (typeOfTarget == 0 && individual.getClass() == Predator.class)
                return -1;
            if (individual.getClass() == Prey.class && this.lifetimeTacts - this.currentAge >= distance)
                return 1;
        }
        if (typeOfTarget == 1 || typeOfTarget == 2) {
            if (individual.getClass() == Prey.class && individual.getLifetimeTacts() - individual.getCurrentAge() >= (distance + 1) / 2)
                return -1;
        }
        return 0;
    }

    public void eatPrey() {

    }

}