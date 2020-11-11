package pl.lasota.database.helper;

import java.util.*;

import lombok.Data;

import pl.lasota.database.mapping.NotUpdating;

@Data
public class TestNotMapping {

    private ObjectTest objectTest;

    @NotUpdating
    private String color;

    private List<String> stringList;
}
