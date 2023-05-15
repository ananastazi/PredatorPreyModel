package com.coursework.predatorpreymodel.classes;

import eu.hansolo.toolbox.tuples.Pair;
import javafx.geometry.Pos;

import java.text.ParsePosition;
import java.util.*;

public abstract class Species {
    protected Integer id;
    protected Position pos;
    protected Pair<Position, Integer> target = null; // target position and id
    protected Integer typeOfTarget; // 0 - prey; 1 - prey/partner; 2 - partner;
    protected List<Position> workaround = null;
    protected Integer criticalEnergy;
    protected Integer energy;
    protected Integer maxEnergy;
    protected Integer amountOfEnergyForReproduction = 6;
    protected Integer amountOfEnergyForMovement = 1;
    protected Integer lifetimeTacts;
    protected Boolean state = true;
    protected Integer currentAge = 1;
    protected Integer causeOfDeath; // cause of death: 0 - from age, 1 - from hunger, 2 - got eaten
    protected Integer searchRadius;

    public Species(Integer id, Position pos, Integer maxEnergy, Integer criticalEnergy, Integer lifetimeTacts, Integer searchRadius) {
        this.id = id;
        this.pos = pos;
        this.criticalEnergy = criticalEnergy;
        this.maxEnergy = maxEnergy;
        this.energy = criticalEnergy + 4;
        this.lifetimeTacts = lifetimeTacts;
        this.searchRadius = searchRadius;
    }

    // region Getters/Setters
    public Integer getId() {
        return id;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Pair<Position, Integer> getTarget() {
        return target;
    }

    public void setTarget(Pair<Position, Integer> target) {
        this.target = target;
    }

    public Integer getTypeOfTarget() {
        return typeOfTarget;
    }

    public void setTypeOfTarget(Integer typeOfTarget) {
        this.typeOfTarget = typeOfTarget;
    }

    public Integer getEnergy() {
        return energy;
    }

    public void setEnergy(Integer energy) {
        this.energy = energy;
    }

    public Integer getCriticalEnergy() {
        return criticalEnergy;
    }

    public Integer getAmountOfEnergyForReproduction() {
        return amountOfEnergyForReproduction;
    }

    public Integer getLifetimeTacts() {
        return lifetimeTacts;
    }

    public Boolean getState() {
        return state;
    }

    public Integer getCurrentAge() {
        return currentAge;
    }

    public Integer getMaxEnergy() {
        return maxEnergy;
    }


    public Integer getCauseOfDeath() {
        return causeOfDeath;
    }
    // endregion

    public void act() {
        if (!state)
            return;
        if (this.currentAge > this.lifetimeTacts) {
            this.dieFrom(0);
            return;
        }
        else if (this.energy < this.criticalEnergy) {
            this.dieFrom(1);
            return;
        }
        else if (this.energy < this.amountOfEnergyForReproduction) {
            this.typeOfTarget = 0;
        }
        else if (this.energy < this.maxEnergy) {
            this.typeOfTarget = 1;
        }
        else {
            this.typeOfTarget = 2;
        }
        currentAge++;
        this.searchTarget();

        this.doStep();
    }

    protected void move() {
        if (workaround != null && this.workaround.size() > 1) {
            Position nextPos = this.workaround.get(this.workaround.size() - 2);
            Cell nextCell = PlayField.getInstance().getField().get(nextPos.getX()).get(nextPos.getY());
            if (nextCell.getIndividual() == null) {
                PlayField.getInstance().getField().get(this.pos.getX()).get(this.pos.getY()).setIndividual(null); // this moves from the cell
                QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(this.pos, nextPos))); // adding change to the queue
                pos = nextPos; // this moves to the cell
                nextCell.setIndividual(this); // to adjust the field
                this.energy -= this.amountOfEnergyForMovement;
            }
        }
    };

    protected void moveRandomly(int[] rowNum, int[] colNum ) { // if the predator goes in diagonal
        Random rnd = new Random();
        int i = rnd.nextInt(4);
        for (int j = 0; j < rowNum.length; j++) {
            i++; i %= rowNum.length;
            if (this.pos.getX() + rowNum[i] >= PlayField.getInstance().getHeight() || this.pos.getX() + rowNum[i] < 0)
                continue;
            if (this.pos.getY() + colNum[i] >= PlayField.getInstance().getWidth() || this.pos.getY() + colNum[i] < 0)
                continue;
            Position nextPos = new Position(this.pos.getX() + rowNum[i], this.pos.getY() + colNum[i]);
            Cell nextCell = PlayField.getInstance().getField().get(nextPos.getX()).get(nextPos.getY());
            if (nextCell.getIndividual() == null) {
                PlayField.getInstance().getField().get(this.pos.getX()).get(this.pos.getY()).setIndividual(null); // this moves from the cell
                QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(this.pos, nextPos))); // adding change to the queue
                this.pos = nextPos; // this moves to the cell
                nextCell.setIndividual(this); // to adjust the field
                this.energy -= this.amountOfEnergyForMovement;
                break;
            }
        }
    }

    public abstract void doStep();

    public void searchTarget() {
        PlayField playField = PlayField.getInstance();
        Integer[][] localMatrix = new Integer[playField.getHeight()][playField.getWidth()];

        for (int i = 0; i < playField.getHeight(); i++) {
            for (int j = 0; j < playField.getWidth(); j++) {
                localMatrix[i][j] = -1;
            }
        }

        Queue<Position> queue = new LinkedList<>();

        Position visitCell = new Position(this.pos.getX(), this.pos.getY());
        localMatrix[this.pos.getX()][this.pos.getY()] = 0;
        queue.add(visitCell);

        int[] rowNum = {-1, 0, 0, 1};
        int[] colNum = {0, -1, 1, 0};

        boolean isTargetFound = false;
        Pair <Integer, Integer> lastCheck = CheckCell(visitCell, 0);
        if (lastCheck.getA() == 1)
            isTargetFound = true;
        for (int  i = 0; i < this.searchRadius && !isTargetFound; ++i) {
            int qSize = queue.size();
            for (int k = 0; !queue.isEmpty() && k < qSize && !isTargetFound; ++k) {
                for (int j = 0; j < rowNum.length && !isTargetFound; j++) {
                    visitCell = new Position(queue.element().getX() + rowNum[j], queue.element().getY() + colNum[j]);
                    if (visitCell.getX() < localMatrix.length && visitCell.getX() >= 0
                            && visitCell.getY() < localMatrix[0].length && visitCell.getY() >= 0 // if the cell is inside the matrix
                            && localMatrix[visitCell.getX()][visitCell.getY()] == -1) {
                        lastCheck = CheckCell(visitCell, i);
                        switch(lastCheck.getA()) {
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

        if (!isTargetFound) {
            this.target = null;
            this.workaround = null;
            return;
        }

        this.target = new Pair<>(visitCell, lastCheck.getB());

        this.workaround = new ArrayList<>();
        this.workaround.add(new Position(visitCell.getX(), visitCell.getY()));
        for (int i = localMatrix[visitCell.getX()][visitCell.getY()]; i > 0; --i) {
            for (int j = 0; j < rowNum.length; j++) {
                if (workaround.get(workaround.size() - 1).getX() + rowNum[j] < localMatrix.length && workaround.get(workaround.size() - 1).getX() + rowNum[j] >= 0
                        && workaround.get(workaround.size() - 1).getY() + colNum[j] < localMatrix[0].length && workaround.get(workaround.size() - 1).getY() + colNum[j] >= 0 // if the cell is inside the matrix
                        && localMatrix[workaround.get(workaround.size() - 1).getX() + rowNum[j]][workaround.get(workaround.size() - 1).getY() + colNum[j]] == (i - 1)) {
                    workaround.add(new Position(workaround.get(workaround.size() - 1).getX() + rowNum[j], workaround.get(workaround.size() - 1).getY() + colNum[j]));
                    break;
                }
            }
        }
    }
    protected abstract Pair<Integer, Integer> CheckCell(Position pos, Integer distance);

    public void createOffspring() {

    }

    public void dieFrom(Integer causeOfDeath) {
        System.out.println("The " + this.getClass() + " with id: " + this.id + " (" + this.pos.getX() + ", " + this.pos.getY() + ") - died due to " + causeOfDeath);
        this.causeOfDeath = causeOfDeath;
        this.state = false;
        QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(this.pos, null)));
        PlayField.getInstance().getField().get(this.pos.getX()).get(this.pos.getY()).setIndividual(null); // this deletes individual from the cell
    }
}
