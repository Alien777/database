package com.tools.database;


import java.io.Serializable;
import java.util.List;


public class PageDTO<ELEMENT_TYPE> implements Serializable {

    private List<ELEMENT_TYPE> objects;
    private long totalOfElement;

    public PageDTO() {
    }

    public PageDTO(List<ELEMENT_TYPE> objects, long totalOfElement) {

        this.objects = objects;
        this.totalOfElement = totalOfElement;
    }

    public List<ELEMENT_TYPE> getObjects() {
        return objects;
    }

    public void setObjects(List<ELEMENT_TYPE> objects) {
        this.objects = objects;
    }

    public long getTotalOfElement() {
        return totalOfElement;
    }

    public void setTotalOfElement(long totalOfElement) {
        this.totalOfElement = totalOfElement;
    }

    public PageDTO<ELEMENT_TYPE> addPage(PageDTO<ELEMENT_TYPE> page) {
        totalOfElement += page.getTotalOfElement();
        objects.addAll(page.getObjects());
        return this;
    }
}
