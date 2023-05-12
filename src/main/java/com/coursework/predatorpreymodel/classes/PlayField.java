package com.coursework.predatorpreymodel.classes;

import java.util.List;

public class PlayField {
    public List<List<Cell>> playField;

    public PlayField(Integer rows, Integer cols) {
//        this.playField = new List<List<Cell>>(rows);
    }
}

class Cell {
    // 0 - empty; 1 - groundcover;
    private Integer typeOfCell = 0; //if the cell is empty or filled with a groundcover
    private Predator predator; // null - empty
    private Prey prey; // null - empty

    public Cell(Integer typeOfCell) {
        this.typeOfCell = typeOfCell;
    }

    public Integer getTypeOfCell() {
        return typeOfCell;
    }

    public void setTypeOfCell(Integer typeOfCell) {
        this.typeOfCell = typeOfCell;
    }

    public void setPredator(Predator predator) {
        this.predator = predator;
    }

    public Predator getPredator() {
        return predator;
    }

    public void setPrey(Prey prey) {
        this.prey = prey;
    }

    public Prey getPrey() {
        return prey;
    }
}
