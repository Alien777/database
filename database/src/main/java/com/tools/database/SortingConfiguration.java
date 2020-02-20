package com.tools.database;

import org.hibernate.search.spatial.Coordinates;

public interface SortingConfiguration {

    void byDistance(String field, double distance, Coordinates coordinates);

   default void byField(String field, boolean type){

   }
}
