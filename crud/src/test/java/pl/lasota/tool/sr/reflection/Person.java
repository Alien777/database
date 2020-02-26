package pl.lasota.tool.sr.reflection;

import lombok.Data;

import java.util.List;

@Data
public class Person {

    @TestAnnotation
    private String name;

    private String secondName;

    private School school;

}
