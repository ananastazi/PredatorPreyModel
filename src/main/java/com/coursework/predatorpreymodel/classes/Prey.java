package com.coursework.predatorpreymodel.classes;

import eu.hansolo.toolbox.tuples.Pair;

public class Prey extends Species {
    protected Integer amountOfEnergyForGrass;
    public Prey(Integer id, Position pos, Integer energy, Integer criticalEnergy, Integer lifetimeTacts, Integer searchRadius) {
        super(id, pos, energy, criticalEnergy, lifetimeTacts, searchRadius);
        this.amountOfEnergyForGrass = 3;
    }

    @Override
    public  void move() {
        if (workaround != null && workaround.size() > 1) {
            Position nextPos = workaround.get(workaround.size() - 2);
            if (PlayField.getInstance().getField().get(nextPos.getX()).get(nextPos.getY()).getIndividual() == null) {
                System.out.println("actual move");
                PlayField.getInstance().getField().get(pos.getX()).get(pos.getY()).setIndividual(null); // this moves from the cell
                pos = nextPos; // this moves to the cell
                PlayField.getInstance().getField().get(pos.getX()).get(pos.getY()).setIndividual(this); // because our pos has been changed
                this.energy -= this.amountOfEnergyForMovement;
            }
        }
    }

    @Override
    public void doStep() {
        if (this.target == null) {
            System.out.println("move at least randomly, please");
            int[] rowNum = {-1, 0, 0, 1};
            int[] colNum = {0, -1, 1, 0};
            System.out.println("random move !!!!");
            this.moveRandomly(rowNum, colNum);
            return;
        }

        if (this.pos.getDistance(this.target.getA()) > 0) {
            System.out.println("ordinary move !!!!");
            this.move();
        }

        if (this.pos.getDistance(this.target.getA()) <= 1) {
            if (this.typeOfTarget == 0) {
                this.eatGrass();
            }
            if (this.typeOfTarget == 1) {
                if (PlayField.getInstance().getField().get(target.getA().getX()).get(target.getA().getY()).getGrass() != null
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
        if (typeOfTarget == 0 || typeOfTarget == 1 && cell.getGrass() != null) { // if the target is grass
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
