package com.coursework.predatorpreymodel.classes;

public class Predator extends Species {
    public Predator(Integer id, Position pos, Integer energy, Integer criticalEnergy, Integer lifetimeTacts) {
        super(id, pos, energy, criticalEnergy, lifetimeTacts);
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
    public void searchTarget(Integer targetType) {
        // local map/matrix filled with -1;
        // check target
    }

    private Integer CheckCell(Position pos) {
        // if filled, is it enough energy, age to get to
        // checking typeOfTarget too
        // -1 - filled; 0 - empty; 1 - target;
        return 0;
    }

    public void eatPrey() {

    }

}