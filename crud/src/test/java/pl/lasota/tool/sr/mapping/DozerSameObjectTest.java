package pl.lasota.tool.sr.mapping;

import org.junit.Test;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.helper.ObjectTest;
import pl.lasota.tool.sr.helper.TestNotMapping;
import pl.lasota.tool.sr.security.Access;
import pl.lasota.tool.sr.service.security.DefaultProvidingRules;

import java.util.HashSet;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class DozerSameObjectTest {

    @Test
    public void notMappingIfAnnotation() {
        TestNotMapping entit = new TestNotMapping();
        entit.setColor("TEST");
        TestNotMapping dest = new TestNotMapping();
        DozerSameObject<TestNotMapping> object = new DozerSameObject<>(TestNotMapping.class);
        object.mapper(entit, dest);

        assertThat(dest.getColor()).isNull();
    }

    @Test
    public void accessNotMapping() {

        Entit toUpdate = new Entit();
        toUpdate.setColor("NOWY KOLOR");
        toUpdate.setId(1L);
        HashSet<Access> toUpdateAccesses = new HashSet<>();
        toUpdateAccesses.add(new Access("role", (short) 4, new DefaultProvidingRules().create("role", (short) 4)));
        toUpdate.setAccesses(toUpdateAccesses);


        Entit toOld = new Entit();
        toOld.setId(2L);
        toOld.setColor("STARY  KOLOR");
        HashSet<Access> toOldAccesses = new HashSet<>();
        toOldAccesses.add(new Access("stary_role", (short) 5, new DefaultProvidingRules().create("stary_role", (short) 5)));
        toUpdate.setAccesses(toOldAccesses);


        DozerSameObject<Entit> object = new DozerSameObject<>(Entit.class);
        object.mapper(toUpdate, toOld);

        assertThat(toOld.getColor()).isEqualTo("NOWY KOLOR");
        assertThat(toOld.getId()).isEqualTo(2L);
        assertThat(toOld.getAccesses())
                .hasSize(1)
                .extracting((Function<Access, String>) Access::getPrivilegeRud)
                .contains("stary_role___5");

    }


    @Test
    public void notMappingIFAnnotationIsSecondLevel() {

        TestNotMapping oldC = new TestNotMapping();
        ObjectTest objectTest = new ObjectTest();
        objectTest.setColorSecond("OLD COLOR");
        oldC.setObjectTest(objectTest);


        TestNotMapping newC = new TestNotMapping();
        ObjectTest objectTestC = new ObjectTest();
        objectTestC.setColorSecond(null);
        newC.setObjectTest(objectTestC);


        DozerSameObject<TestNotMapping> object = new DozerSameObject<>(TestNotMapping.class);
        object.mapper(newC, oldC);

        assertThat(oldC.getObjectTest().getColorSecond()).isEqualTo("OLD COLOR");
    }

}
