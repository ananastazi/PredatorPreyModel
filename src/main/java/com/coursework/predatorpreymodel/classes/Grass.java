package com.coursework.predatorpreymodel.classes;

import javafx.util.Pair;
import java.util.Random;

public class Grass {
    protected Position pos;
    protected Integer id;
    protected Boolean state = true;
    protected Integer deathtimeTacts;

    public Grass(Position pos, Integer id) {
        this.pos = pos;
        this.id = id;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public Integer getDeathtimeTacts() {
        return deathtimeTacts;
    }

    public void setDeathtimeTacts(Integer deathtimeTacts) {
        this.deathtimeTacts = deathtimeTacts;
    }

    //grass renew with some probability
    void renewState() { //or with parameter rand
        Random rand = new Random();
        if (deathtimeTacts > 3 && (rand.nextInt() % (6 - deathtimeTacts) == 0))
            this.state = true;
    }
}
