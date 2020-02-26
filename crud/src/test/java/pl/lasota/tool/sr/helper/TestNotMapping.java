package pl.lasota.tool.sr.helper;

import java.util.*;

import lombok.Data;

import pl.lasota.tool.sr.mapping.NotUpdating;

@Data
public class TestNotMapping {

    private ObjectTest objectTest;

    @NotUpdating
    private String color;

    private List<String> stringList;
}
