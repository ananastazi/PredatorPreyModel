package com.coursework.predatorpreymodel.classes;

public class UniqueIdGenerator {
    Integer uniqueId;
    private static UniqueIdGenerator single_instance = null;
    // private generator to be called within this class
    private UniqueIdGenerator() { uniqueId = 0; }
    // Static method to create instance of Singleton class
    public static synchronized UniqueIdGenerator getInstance()
    {
        if (single_instance == null)
            single_instance = new UniqueIdGenerator();
        return single_instance;
    }
    public Integer getNewUniqueId() {
        this.uniqueId++;
        return this.uniqueId - 1;
    }
}
