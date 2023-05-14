package com.coursework.predatorpreymodel;

import com.coursework.predatorpreymodel.classes.Position;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class UniquePositionGenerator {
    private Integer[] nodes;
    private Integer width;
    private Integer height;
    Random random = new Random();

    // initializing nodes with numbers from 1 to width * length
    // addition 1st element, elements of matrix, addition last element
    public UniquePositionGenerator(Integer width, Integer height) {
        this.width = width;
        this.height = height;
        nodes = new Integer[height * width];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = i;
        }
    }
    public Position GetNewPos() {
        Integer rndIndex = Root(random.nextInt(nodes.length));
        Union(rndIndex, (rndIndex + 1) % nodes.length);
        return new Position(rndIndex / width, rndIndex % width);
    }

    // find the root of nodes[x] which is presented as a tree;
    private Integer Root(Integer x)
    {
        Integer res = nodes[x];
        while (res != nodes[res])
        {
            nodes[res] = nodes[nodes[res]];
            res = nodes[res];
        }
        return res;
    }

    // unite connectivity component of node[x] with one of node[y]
    private void Union(int x, int y)
    {
        int rootX = Root(x);
        int rootY = Root(y);
        nodes[max(rootX, rootY)] = min(rootX, rootY);
    }
}
