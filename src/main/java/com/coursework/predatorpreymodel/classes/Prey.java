package com.coursework.predatorpreymodel.classes;

public class Prey extends Species {
    public Prey(Integer id, Position pos, Integer energy, Integer criticalEnergy, Integer lifetimeTacts, Integer searchRadius) {
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
                this.eatGrass();
            } else {
                this.createOffspring();
            }
        }
    }

    @Override
    protected Integer CheckCell(Position pos, Integer distance) {
        // if filled, is it enough energy, age to get to
        // checking typeOfTarget too
        // -1 - filled; 0 - empty; 1 - target;
        return 0;
    }
    public void eatGrass() {

    }
}
