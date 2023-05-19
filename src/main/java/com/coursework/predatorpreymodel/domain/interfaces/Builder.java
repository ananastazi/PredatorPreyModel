package com.coursework.predatorpreymodel.domain.interfaces;

import com.coursework.predatorpreymodel.domain.cell.Position;

public interface Builder {
    public void addGrass(Position pos);
    public void addPredator(Position pos, Integer energy);
    public void addPrey(Position pos, Integer energy);
}