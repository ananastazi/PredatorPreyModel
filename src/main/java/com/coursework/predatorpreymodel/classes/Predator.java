package com.coursework.predatorpreymodel.classes;

import java.util.ArrayList;
import java.util.Collections;

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
    public void searchTarget() {
        // local map/matrix filled with -1;
        ArrayList<ArrayList<Integer>> localMatrix = new ArrayList<>();
        PlayField playField = PlayField.getInstance();

        for (int i = 0; i < playField.getWidth(); i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < playField.getLength(); j++) {
                row.add(-1);
            }
            localMatrix.add(row);
        }

        localMatrix.get(this.pos.getX()).set(this.pos.getY(), 0);


        //  create queue with poss, find target, find workaround
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