package com.coursework.predatorpreymodel.domain.individuals;

import com.coursework.predatorpreymodel.infrastructure.PlayField;
import com.coursework.predatorpreymodel.domain.cell.Cell;
import com.coursework.predatorpreymodel.domain.interfaces.Builder;
import com.coursework.predatorpreymodel.utils.Position;
import eu.hansolo.toolbox.tuples.Pair;

public class Prey extends Species {
    protected Integer amountOfEnergyForGrass;
    public Prey(Builder builder, Integer id, Position pos, Integer criticalEnergy, Integer energy, Integer maxEnergy, Integer lifetimeTacts, Integer searchRadius) {
        super(builder, id, pos, criticalEnergy, energy, maxEnergy, lifetimeTacts, searchRadius);
        this.amountOfEnergyForGrass = 6;
    }

    @Override
    public void doStep() {
        if (this.target == null) {
            int[] rowNum = {-1, 0, 0, 1};
            int[] colNum = {0, -1, 1, 0};
            this.moveRandomly(rowNum, colNum);
            return;
        }

        if (this.pos.getDistance(this.target.getA()) > 0) {
            this.move();
        }

        if (this.pos.getDistance(this.target.getA()) <= 1) {
            if (this.pos.equals(target.getA()) && this.typeOfTarget == 0) { // to make sure grass is located in the same cell
                this.eatGrass();
            }
            if (this.typeOfTarget == 1) {
                if (this.pos.equals(target.getA()) // to make sure grass is located in the same cell
                        && PlayField.getInstance().getField().get(target.getA().getX()).get(target.getA().getY()).getGrass() != null
                        && target.getB() == PlayField.getInstance().getField().get(target.getA().getX()).get(target.getA().getY()).getGrass().getId())
                    this.eatGrass();
                if (target.getB() == PlayField.getInstance().getField().get(target.getA().getX()).get(target.getA().getY()).getIndividual().getId())
                    this.createOffspring();
            }
            if (this.typeOfTarget == 2) {
                this.createOffspring();
            }
        }
    }

    @Override
    protected Pair<Integer, Integer> CheckCell(Position pos, Integer distance) {
        Cell cell = PlayField.getInstance().getField().get(pos.getX()).get(pos.getY());
        Species individual = cell.getIndividual();
        if (individual == null)
            return new Pair<>(0, null);

        if ((typeOfTarget == 1 || typeOfTarget == 2) && individual.getClass() == Prey.class && !this.equals(individual)) { // if the target is a partner
            // (distance + 1) / 2 - is used because 7 / 2 = 3, but you will need 4 steps to get to the target
            if (this.lifetimeTacts - this.currentAge >= (distance + 1) / 2 // if the hunter can get to the target in the best-case scenario
                    && this.energy - this.amountOfEnergyForMovement * ((distance + 1) / 2) >= this.amountOfEnergyForReproduction // if the amount of energy is sufficient
                    && this.energy - this.amountOfEnergyForMovement * (distance + 1) / 2 >= this.criticalEnergy // if the hunter can reach the partner in the best-case scenario
                    && individual.energy - individual.amountOfEnergyForMovement * (distance + 1) / 2 >= individual.criticalEnergy // if the target can reach the partner in the best-case scenario
                    && individual.energy - individual.amountOfEnergyForMovement * ((distance + 1) / 2) >= individual.amountOfEnergyForReproduction) // if the amount of energy is sufficient
                return new Pair<>(1, individual.getId());
        }
        if ((typeOfTarget == 0 || typeOfTarget == 1) && cell.getGrass() != null) { // if the target is grass
            if (cell.getTypeOfCell() == 1 && this.lifetimeTacts - this.currentAge >= distance // if the prey can get to the grass in the best-case scenario
                    && this.energy - this.amountOfEnergyForMovement * distance >= this.criticalEnergy) // if the hunter can reach the partner in the best-case scenario
                return new Pair<>(1, cell.getGrass().getId());
        }
        return new Pair<>(-1, null);
    }

    public void eatGrass() {
        this.energy += this.amountOfEnergyForGrass;
        PlayField.getInstance().getField().get(target.getA().getX()).get(target.getA().getY()).getGrass().dieFrom(2);
    }
}
