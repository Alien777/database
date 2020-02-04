package pl.lasota.tool.sr.reflection;


import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import pl.lasota.tool.sr.field.*;
import pl.lasota.tool.sr.mapping.CopyByReference;
import pl.lasota.tool.sr.parser.ParserField;

import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilsReflectionTest {



    @Test
    public void parser() throws Exception {
        UtilsReflection.findAllFieldsContains(Blog.class, CopyByReference.class, new Consumer<FieldNode>() {
            @Override
            public void accept(FieldNode fieldNode) {
                System.out.println("dsadsadas");
            }
        });
    }

}
