package com.coursework.predatorpreymodel.classes;

import javafx.util.Pair;
import java.util.Random;
public class Grass {
    protected Pair<Integer, Integer> pos;
    protected Integer id;
    protected Boolean state;
    protected Integer deathtimeTacts;

    public Pair<Integer, Integer> getPos() {
        return pos;
    }

    public void setPos(Pair<Integer, Integer> pos) {
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
