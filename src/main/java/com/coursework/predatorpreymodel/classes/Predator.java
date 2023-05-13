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
    public void searchTarget() {
        PlayField playField = PlayField.getInstance();
        Integer[][] localMatrix = new Integer[playField.getHeight()][playField.getWidth()];

        for (int i = 0; i < playField.getHeight(); i++) {
            for (int j = 0; j < playField.getWidth(); j++) {
                localMatrix[i][j] = -1;
            }
        }

        Queue<Position> queue = new LinkedList<>();

        localMatrix[this.pos.getX()][this.pos.getY()] = 0;
        queue.add(new Position(this.pos.getX(), this.pos.getY()));

        int rowNum[] = {-1, 0, 0, 1};
        int colNum[] = {0, -1, 1, 0};

        Boolean isTargetFound = false;
        Position visitCell =  new Position(-1, -1);

        for (int  i = 0; i < this.searchRadius && !isTargetFound; ++i) {
            int qSize = queue.size();
            for (int k = 0; !queue.isEmpty() && k < qSize && !isTargetFound; ++k) {
                for (int j = 0; j < rowNum.length && !isTargetFound; j++) {
                    visitCell = new Position(queue.element().getX() + rowNum[j], queue.element().getY() + colNum[j]);
                    if (localMatrix[visitCell.getX()][visitCell.getY()] == -1) {
                        switch(CheckCell(visitCell)) {
                            case -1:
                                localMatrix[visitCell.getX()][visitCell.getY()] = -2;
                                break;
                            case 0:
                                queue.add(visitCell);
                                localMatrix[visitCell.getX()][visitCell.getY()] = i + 1;
                                break;
                            case 1:
                                isTargetFound = true;
                                localMatrix[visitCell.getX()][visitCell.getY()] = i + 1;
                                break;
                        }
                    }
                }
                queue.remove();
            }
        }

        ArrayList<Position> workaround = new ArrayList<>();
        for (int i = localMatrix[visitCell.getX()][visitCell.getY()]; i > 0; --i) {
            for (int j = 0; j < rowNum.length; j++) {
                if (localMatrix[workaround.get(workaround.size() - 1).getX() + rowNum[j]][workaround.get(workaround.size() - 1).getY() + colNum[j]] == (i - 1)) {
                    workaround.add(new Position(i + rowNum[j], j + colNum[j]));
                    break;
                }
            }
        }

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