package pl.lasota.tool.sr.reflection;

import lombok.Data;

@Data
public class Blog extends BlogParent {

    @TestAnnotation
    private String url;

    private Person person;


}
