package pl.lasota.tool.sr.mapping;

import org.junit.Test;
import pl.lasota.tool.sr.helper.TestNotMapping;

import static org.assertj.core.api.Assertions.assertThat;

public class DozerSameObjectTest {

    @Test
    public void notMappingIfAnnotation() {
        TestNotMapping entit = new TestNotMapping();
        entit.setColor("TEST");
        DozerSameObject object = new DozerSameObject<>(TestNotMapping.class);
        TestNotMapping dest = new TestNotMapping();
        object.mapper(entit, dest);
        assertThat(dest.getColor()).isNull();
    }

    @Test
    public void copyByReferenceIfAnnotation() {
//        TestNotMapping entit = new TestNotMapping();
//        entit.setColor("TEST");
//        DozerSameObject object = new DozerSameObject<>(TestNotMapping.class);
//
//        TestNotMapping dest = new TestNotMapping();
//        object.mapper(entit, dest);
//        assertThat(dest.getColor()).isNull();
    }

}
