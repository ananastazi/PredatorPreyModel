package com.coursework.predatorpreymodel.classes;

public class Prey extends Species {
    public Prey(Integer id, Position pos, Integer energy, Integer criticalEnergy, Integer lifetimeTacts) {
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
                this.eatGrass();
            } else {
                this.createOffspring();
            }
        }
    }

    @Override
    public void searchTarget(Integer targetType) {

    }

    public void eatGrass() {

    }
}
