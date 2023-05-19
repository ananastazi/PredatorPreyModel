package com.coursework.predatorpreymodel.infrastructure;

import com.coursework.predatorpreymodel.domain.cell.Cell;
import com.coursework.predatorpreymodel.domain.cell.Position;
import com.coursework.predatorpreymodel.domain.individuals.Predator;
import com.coursework.predatorpreymodel.domain.individuals.Prey;

import java.util.ArrayList;
import java.util.List;

// SINGLETON CLASS
public class PlayField {
    private static PlayField single_instance = null; // Static variable reference of single_instance of type Singleton
    private List<List<Cell>> field;
    private Integer width, height;

    private PlayField() { }

    // Static method to create instance of Singleton class
    public static synchronized PlayField getInstance() {
        if (single_instance == null)
            single_instance = new PlayField();
        return single_instance;
    }

    public void setPlayField(Integer height, Integer width) {
        this.height = height;
        this.width = width;
        this.field = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<Cell> innerList = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                innerList.add(new Cell());
            }
            this.field.add(innerList);
        }
    }

    public void resetPlayField() {
        this.field = null;
        this.width = 0;
        this.height = 0;
    }

    public List<List<Cell>> getField() { return field; }
    public Cell getCell(Position pos) { return field.get(pos.getX()).get(pos.getY()); }

    public Integer getHeight() {
        return height;
    }

    public Integer getWidth() {
        return width;
    }

    public void printPlayField() {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";

        System.out.println("Current field is:");
        for (int i = 0; i < this.getHeight(); ++i) {
            for (int j = 0; j < this.getWidth(); ++j) {
                Cell cell = this.getField().get(i).get(j);
                switch (cell.getTypeOfCell()) {
                    case 0:
                        // set ground Image to pane
                        System.out.print(ANSI_RESET);
                        break;
                    case 1:
                        // set grass Image to pane
                        System.out.print(ANSI_GREEN);
                        break;
                }

                System.out.print(" ");
                if (cell.getIndividual() == null) {
                    System.out.print(0);
                    continue;
                }

                if (cell.getIndividual().getClass() == Predator.class) {
                    System.out.print(2);
                }
                if (cell.getIndividual().getClass() == Prey.class) {
                    System.out.print(1);
                }
            }
            System.out.println(ANSI_RESET);
        }
        System.out.println();
    }
}

