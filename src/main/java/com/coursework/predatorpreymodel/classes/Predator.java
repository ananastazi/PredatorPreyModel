package com.coursework.predatorpreymodel.classes;

import com.coursework.predatorpreymodel.AlertBox;
import eu.hansolo.toolbox.tuples.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Predator extends Species {
    private Integer amountOfEnergyForPrey;

    public Predator(Integer id, Position pos, Integer energy, Integer criticalEnergy, Integer lifetimeTacts, Integer searchRadius) {
        super(id, pos, energy, criticalEnergy, lifetimeTacts, searchRadius);
        this.amountOfEnergyForPrey = 5;
    }

    @Override
    // Probably going to move in diagonal
    public  void move() {
        if (this.workaround != null && this.workaround.size() > 1) {
            Position nextPos = this.workaround.get(this.workaround.size() - 2);
            Cell nextCell = PlayField.getInstance().getField().get(nextPos.getX()).get(nextPos.getY());
            if (nextCell.getIndividual() == null) {
                PlayField.getInstance().getField().get(pos.getX()).get(pos.getY()).setIndividual(null); // this moves from the cell
                QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(pos, nextPos))); // adding change to the queue
                pos = nextPos; // this moves to the cell
                nextCell.setIndividual(this); // to adjust the field
                this.energy -= this.amountOfEnergyForMovement;
            }
        }
    }

    @Override
    public void doStep() {
        if (this.target == null){
            int[] rowNum = {-1, 0, 0, 1};
            int[] colNum = {0, -1, 1, 0};
            this.moveRandomly(rowNum, colNum);
            return;
        }

        if (this.pos.getDistance(this.target.getA()) > 1)
            this.move();

        if (this.pos.getDistance(this.target.getA()) == 1) {
            if (this.typeOfTarget == 0) {
                this.eatPrey();
            }
            if (this.typeOfTarget == 1) {
                if (PlayField.getInstance().getField().get(target.getA().getX()).get(target.getA().getY()).getIndividual() == null)
                    AlertBox.display("no-no-no. There is target and no individual in cell");
                if (PlayField.getInstance().getField().get(target.getA().getX()).get(target.getA().getY()).getIndividual().getClass() == Prey.class)
                    this.eatPrey();
                else
                    this.createOffspring();
            }
            if (this.typeOfTarget == 2) {
                this.createOffspring();
            }
        }
    }

    @Override
    // A: -1 - filled; 0 - empty; 1 - target;
    // B: null or actual id
    protected Pair<Integer, Integer> CheckCell(Position pos, Integer distance) {
        if (this.lifetimeTacts - this.currentAge < (distance + 1) / 2) // if the hunter will die before getting to the target in the best-case scenario. Due to age
            return new Pair<>(-1, null);
        if (this.energy - this.amountOfEnergyForMovement * (distance + 1) / 2 < this.criticalEnergy) // if the hunter will die before getting to the target in the best-case scenario. Due to energy
            return new Pair<>(-1, null);

        Species individual = PlayField.getInstance().getField().get(pos.getX()).get(pos.getY()).getIndividual();
        if (individual == null)
            return new Pair<>(0, null);

        if ((typeOfTarget == 1 || typeOfTarget == 2) && individual.getClass() == Predator.class && !this.equals(individual)) { // if the target is a partner
            // (distance + 1) / 2 - is used because 7 / 2 = 3, but you will need 4 steps to get to the target
            if (this.energy - this.amountOfEnergyForMovement * ((distance + 1) / 2) >= this.amountOfEnergyForReproduction // if the amount of energy is sufficient
                    && individual.energy - individual.amountOfEnergyForMovement * (distance + 1) / 2 >= individual.criticalEnergy // if the target can get to the partner in the best-case scenario
                    && individual.energy - individual.amountOfEnergyForMovement * ((distance + 1) / 2) >= individual.amountOfEnergyForReproduction) // if the amount of energy is sufficient
                return new Pair<>(1, individual.getId());
        }
        if ((typeOfTarget == 0 || typeOfTarget == 1) && individual.getClass() == Prey.class) { // if the target is prey
            return new Pair<>(1, individual.getId()); // because hunter will not die in the best-case scenario
        }
        return new Pair<>(-1, null); // the positive outcome has been considered
    }

    public void eatPrey() {
        this.energy += this.amountOfEnergyForPrey;
        PlayField.getInstance().getField().get(target.getA().getX()).get(target.getA().getY()).getIndividual().dieFrom(2);
    }
}