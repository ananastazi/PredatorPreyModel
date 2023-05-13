package com.coursework.predatorpreymodel.classes;

import java.util.ArrayList;
import java.util.List;

// SINGLETON CLASS
public class PlayField {
    private static PlayField single_instance = null; // Static variable reference of single_instance of type Singleton
    private List<List<Cell>> field;
    private Integer width, height;

    private PlayField() { }

    // Static method to create instance of Singleton class
    public static synchronized PlayField getInstance()
    {
        if (single_instance == null)
            single_instance = new PlayField();
        return single_instance;
    }

    public void setPlayField(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        this.field = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            List<Cell> innerList = new ArrayList<>();
            for (int j = 0; j < height; j++) {
                innerList.add(new Cell());
            }
            this.field.add(innerList);
        }
    }

    public List<List<Cell>> getField() { return field; }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }
}

