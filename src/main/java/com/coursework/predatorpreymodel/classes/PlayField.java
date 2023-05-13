package com.coursework.predatorpreymodel.classes;

import java.util.ArrayList;
import java.util.List;

// MAKE IT SINGLETON
public class PlayField {
    public List<List<Cell>> playField;

    public PlayField(Integer rows, Integer cols) {
        this.playField = new ArrayList<>();
        for (int i = 0; i < rows; i++) {
            List<Cell> innerList = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                innerList.add(new Cell());
            }
            this.playField.add(innerList);
        }
    }
}

class Cell {
    // 0 - empty; 1 - groundcover;
    private Integer typeOfCell = 0; //if the cell is empty or filled with a groundcover

//    private Species predator; // null - empty
    private Predator predator; // null - empty
    private Prey prey; // null - empty

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
