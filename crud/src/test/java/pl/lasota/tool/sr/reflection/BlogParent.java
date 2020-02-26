package pl.lasota.tool.sr.reflection;

import lombok.Data;

@Data
public class BlogParent extends BlogBase {

    @TestAnnotation
    private String fieldParent;
}
