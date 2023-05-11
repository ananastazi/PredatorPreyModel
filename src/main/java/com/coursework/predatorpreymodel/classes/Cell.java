package com.coursework.predatorpreymodel.classes;

public class Cell {
    private Integer  typeOfCell; //if the cell is empty or filled with a groundcover
    private Integer  numberOfPredators; //number of predators in the cell
    private Integer  numberOfPreys; //number of preys in the cell

    public Cell(Integer typeOfCell, Integer numberOfPredators, Integer numberOfPreys) {
        this.typeOfCell = typeOfCell;
        this.numberOfPredators = numberOfPredators;
        this.numberOfPreys = numberOfPreys;
    }

    public Integer getTypeOfCell() {
        return typeOfCell;
    }

    public void setTypeOfCell(Integer typeOfCell) {
        this.typeOfCell = typeOfCell;
    }

    public Integer getNumberOfPredators() {
        return numberOfPredators;
    }

    public void setNumberOfPredators(Integer numberOfPredators) {
        this.numberOfPredators = numberOfPredators;
    }

    public Integer getNumberOfPreys() {
        return numberOfPreys;
    }

    public void setNumberOfPreys(Integer numberOfPreys) {
        this.numberOfPreys = numberOfPreys;
    }
}
