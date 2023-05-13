package com.coursework.predatorpreymodel.classes;

import java.util.ArrayList;
import java.util.List;

// SINGLETON CLASS
public class PlayField {
    private static PlayField single_instance = null; // Static variable reference of single_instance of type Singleton
    public List<List<Cell>> field;
    private Integer width, length;

    private PlayField() { }

    // Static method to create instance of Singleton class
    public static synchronized PlayField getInstance()
    {
        if (single_instance == null)
            single_instance = new PlayField();
        return single_instance;
    }

    public void setPlayField(Integer width, Integer length) {
        this.width = width;
        this.length = length;
        this.field = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            List<Cell> innerList = new ArrayList<>();
            for (int j = 0; j < length; j++) {
                innerList.add(new Cell());
            }
            this.field.add(innerList);
        }
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getLength() {
        return length;
    }
}

class Cell {
    // 0 - empty; 1 - groundcover;
    private Integer typeOfCell = 0; // if the cell is empty or filled with a groundcover

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
