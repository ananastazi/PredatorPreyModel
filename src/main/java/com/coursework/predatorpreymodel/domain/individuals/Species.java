package com.coursework.predatorpreymodel.domain.individuals;

import com.coursework.predatorpreymodel.infrastructure.PlayField;
import com.coursework.predatorpreymodel.infrastructure.QueueOfChanges;
import com.coursework.predatorpreymodel.domain.cell.Cell;
import com.coursework.predatorpreymodel.domain.interfaces.Builder;
import com.coursework.predatorpreymodel.utils.Position;
import eu.hansolo.toolbox.tuples.Pair;

import java.util.*;

public abstract class Species {
    protected Builder builder;
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

    public Species(Builder builder, Integer id, Position pos, Integer criticalEnergy, Integer energy, Integer maxEnergy, Integer lifetimeTacts, Integer searchRadius) {
        this.builder = builder;
        this.id = id;
        this.pos = pos;
        this.criticalEnergy = criticalEnergy;
        this.maxEnergy = maxEnergy;
        this.energy = energy;
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

    protected void moveRandomly(int[] rowNum, int[] colNum) { // if the predator goes in diagonal
        Position nextPos = getRandomCell(rowNum, colNum);
        if (nextPos != null) {
            PlayField.getInstance().getCell(this.pos).setIndividual(null); // this moves from the cell
            QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(this.pos, nextPos))); // adding change to the queue
            this.pos = nextPos; // this moves to the cell
            PlayField.getInstance().getCell(nextPos).setIndividual(this); // to adjust the field
            this.energy -= this.amountOfEnergyForMovement;
        }
    }

    private Position getRandomCell(int[] rowNum, int[] colNum ) {
        Random rnd = new Random();
        int i = rnd.nextInt(rowNum.length);
        for (int j = 0; j < rowNum.length; j++) {
            i++;
            i %= rowNum.length;
            Position nextPos = new Position(this.pos.getX() + rowNum[i], this.pos.getY() + colNum[i]);
            if (nextPos.getX() >= PlayField.getInstance().getHeight() || nextPos.getX() < 0)
                continue;
            if (nextPos.getY() >= PlayField.getInstance().getWidth() || nextPos.getY() < 0)
                continue;
            Cell nextCell = PlayField.getInstance().getCell(nextPos);
            if (nextCell.getIndividual() == null)
                return nextPos;
        }
        return null;
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
        int[] rowNum, colNum;
        if (this.pos.getX() == target.getA().getX()) {
            int sideShift = target.getA().getY() - this.pos.getY(); // if the target is to the right, then this will be 1. if to the left, then -1
            rowNum = new int[] { 1, 1, -1, -1};
            colNum = new int[] { 0, sideShift, 0, sideShift };
        }
        else {
            int sideShift = target.getA().getX() - this.pos.getX(); // if the target is to the bottom, then this will be 1. if to the top, then -1
            rowNum = new int[] { 0, sideShift, 0, sideShift };
            colNum = new int[] { 1, 1, -1, -1};
        }
        Position pos = getRandomCell(rowNum, colNum);

        Species partner = PlayField.getInstance().getCell(target.getA()).getIndividual();
        partner.energy -= amountOfEnergyForReproduction;
        this.energy -= amountOfEnergyForReproduction;
        if (new Random().nextInt() % 2 == 0) {
            if (this.getClass() == Predator.class)
                builder.addPredator(pos, amountOfEnergyForReproduction * 2);
            if (this.getClass() == Prey.class)
                builder.addPrey(pos, amountOfEnergyForReproduction * 2);
        }
    }

    public void dieFrom(Integer causeOfDeath) {
        System.out.println("The " + this.getClass() + " with id: " + this.id + " (" + this.pos.getX() + ", " + this.pos.getY() + ") - died due to " + causeOfDeath);
        this.causeOfDeath = causeOfDeath;
        this.state = false;
        QueueOfChanges.getInstance().add(new Pair<>(1, new Pair<>(this.pos, null)));
        PlayField.getInstance().getField().get(this.pos.getX()).get(this.pos.getY()).setIndividual(null); // this deletes individual from the cell
    }
}
