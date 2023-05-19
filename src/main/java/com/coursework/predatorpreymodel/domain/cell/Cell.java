package com.coursework.predatorpreymodel.domain.cell;


import com.coursework.predatorpreymodel.domain.cell.typesOfCell.Grass;
import com.coursework.predatorpreymodel.domain.individuals.Species;

public class Cell {
    // 0 - empty; 1 - groundcover;
    private Integer typeOfCell = 0; // if the cell is empty or filled with a groundcover
    private Grass grass;
    private Species individual = null; // null - empty

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

    public Grass getGrass() {
        return grass;
    }

    public void setGrass(Grass grass) {
        this.grass = grass;
    }

}