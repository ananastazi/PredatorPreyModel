package com.coursework.predatorpreymodel.classes;

public interface Builder {
    public void addGrass(Position pos);
    public void addPredator(Position pos, Integer energy);
    public void addPrey(Position pos, Integer energy);
}
