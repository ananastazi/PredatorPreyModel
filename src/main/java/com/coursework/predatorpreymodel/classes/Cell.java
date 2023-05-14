package com.coursework.predatorpreymodel.classes;


public class Cell {
    // 0 - empty; 1 - groundcover;
    private Integer typeOfCell = 0; // if the cell is empty or filled with a groundcover

    private Species individual; // null - empty

    public Integer getTypeOfCell() {
        return typeOfCell;
    }

    public void setTypeOfCell(Integer typeOfCell) {
        this.typeOfCell = typeOfCell;
    }

    public Species getIndividual() {
        return individual;
    }

    public void setIndividual(Species individual) {
        this.individual = individual;
    }
}