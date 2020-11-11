package pl.lasota.database.reflection;

import lombok.Data;

@Data
public class Person {

    @TestAnnotation
    private String name;

    private String secondName;

    private School school;

}
