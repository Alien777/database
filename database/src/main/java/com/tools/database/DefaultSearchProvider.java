package com.tools.database;


import java.util.List;

public abstract class DefaultSearchProvider implements Sortable, Fields {

    private int number;
    private int size;

    public DefaultSearchProvider(int number, int size) {

        this.number = number;
        this.size = size;
    }

    @Override
    public abstract void set(List<Field> fields);

    @Override
    public void sort(SortingConfiguration sortingConfiguration) {
    }

    public int getNumber() {
        return number;
    }

    public int getSize() {
        return size;
    }
}
