package com.coursework.predatorpreymodel.classes;

import static java.lang.Math.abs;

public class Position {
    private Integer x, y;

    public Position(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getDistance(Position pos) {
        return abs(pos.x - this.x) + abs(pos.y - this.y);
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    // Overriding equals() to compare two Position objects
    @Override
    public boolean equals(Object o) {
        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof Position)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        Position pos = (Position)o;

        // Compare the data members and return accordingly
        return Double.compare(this.x, pos.x) == 0
                && Double.compare(this.y, pos.y) == 0;
    }
}
