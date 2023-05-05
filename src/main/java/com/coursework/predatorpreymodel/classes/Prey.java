package com.coursework.predatorpreymodel.classes;

import com.classes.Species;

public class Prey extends Species {
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
