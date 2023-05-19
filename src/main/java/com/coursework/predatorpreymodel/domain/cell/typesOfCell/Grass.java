package com.coursework.predatorpreymodel.domain.cell.typesOfCell;

import com.coursework.predatorpreymodel.infrastructure.PlayField;
import com.coursework.predatorpreymodel.infrastructure.QueueOfChanges;
import com.coursework.predatorpreymodel.domain.cell.Position;
import eu.hansolo.toolbox.tuples.Pair;

import java.util.Random;

public class Grass {
    protected Position pos;
    protected Integer id;
    protected Boolean state = true;
    protected Integer deathtimeTacts;
    private Integer causeOfDeath; // cause of death: 2 - got eaten
    public Grass(Position pos, Integer id) {
        this.pos = pos;
        this.id = id;
    }

    // region Getters/Setters
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
    // endregion

    public void act() {
        if (!state) {
            this.deathtimeTacts++;
            renewState();
        }
    }

    // grass renew with some probability
    void renewState() {
        Random rand = new Random();
        if (deathtimeTacts > 3 && (rand.nextInt() % (8 - deathtimeTacts) == 0)) { // when the deathtimeTacts = 7, then the likelihood is 100%
            this.state = true;
            PlayField.getInstance().getCell(pos).setTypeOfCell(1);
            QueueOfChanges.getInstance().add(new Pair<>(0, new Pair<>(null, pos))); // adding change to the queue
        }
    }

    public void dieFrom(Integer causeOfDeath) {
        System.out.println("The " + this.getClass() + " with id: " + this.id + " (" + this.pos.getX() + ", " + this.pos.getY() + ") - died due to " + causeOfDeath);
        this.causeOfDeath = causeOfDeath;
        this.state = false;
        this.deathtimeTacts = 0;
        PlayField.getInstance().getField().get(this.pos.getX()).get(this.pos.getY()).setGrass(null); // this deletes grass from the cell
        PlayField.getInstance().getField().get(this.pos.getX()).get(this.pos.getY()).setTypeOfCell(0); // this deletes grass from the cell
        QueueOfChanges.getInstance().add(new Pair<>(0, new Pair<>(this.pos, null))); // adding change to the queue
    }
}
