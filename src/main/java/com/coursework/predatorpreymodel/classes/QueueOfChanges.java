package com.coursework.predatorpreymodel.classes;

import eu.hansolo.toolbox.tuples.Pair;

import java.util.LinkedList;
import java.util.Queue;

public class QueueOfChanges { // collects changes to individuals
    private static QueueOfChanges single_instance = null; // Static variable reference of single_instance of type Singleton
    Queue<Pair<Integer, Pair<Position, Position>>> queue;
    // Integer - typeOfChange: 0 - background, 1 - foreground (individual);
    // Pair - Position of: A: changed from, B: changed to;

    private QueueOfChanges() {
        queue = new LinkedList<>();
    }
    // Static method to create instance of Singleton class
    public static synchronized QueueOfChanges getInstance()
    {
        if (single_instance == null)
            single_instance = new QueueOfChanges();
        return single_instance;
    }
    public Integer size() { return queue.size(); }
    public void add(Pair<Integer, Pair<Position, Position>> newElement) {
        queue.add(newElement);
    }
    public Pair<Integer, Pair<Position, Position>> pop() { // deletes and returns first value
        return queue.remove();
    }
}
