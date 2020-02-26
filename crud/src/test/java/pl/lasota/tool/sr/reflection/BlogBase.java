package pl.lasota.tool.sr.reflection;

import lombok.Data;
import java.util.Set;

@Data
public class BlogBase {

    @TestAnnotation
    private String fieldSecondParent;

    @TestAnnotation
    private Set<SchoolSecond> schoolList;
}
