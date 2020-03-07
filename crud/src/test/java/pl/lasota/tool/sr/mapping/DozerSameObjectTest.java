package pl.lasota.tool.sr.mapping;

import org.junit.Test;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.helper.ObjectTest;
import pl.lasota.tool.sr.helper.TestNotMapping;
import pl.lasota.tool.sr.security.SpecialPermission;

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
        HashSet<SpecialPermission> toUpdateSpecialPermissions = new HashSet<>();
        toUpdateSpecialPermissions.add(new SpecialPermission("role", (short) 4));
        toUpdate.setSpecialPermissions(toUpdateSpecialPermissions);


        Entit toOld = new Entit();
        toOld.setId(2L);
        toOld.setColor("STARY  KOLOR");
        HashSet<SpecialPermission> toOldSpecialPermissions = new HashSet<>();
        toOldSpecialPermissions.add(new SpecialPermission("stary_role", (short) 5));
        toOld.setSpecialPermissions(toOldSpecialPermissions);


        DozerSameObject<Entit> object = new DozerSameObject<>(Entit.class);
        object.mapper(toUpdate, toOld);

        assertThat(toOld.getColor()).isEqualTo("NOWY KOLOR");
        assertThat(toOld.getId()).isEqualTo(2L);

        assertThat(toOld.getSpecialPermissions())
                .hasSize(1)
                .extracting((Function<SpecialPermission, String>) SpecialPermission::getPrivileged)
                .contains("stary_role");

        assertThat(toOld.getSpecialPermissions())
                .hasSize(1)
                .extracting((Function<SpecialPermission, Short>) SpecialPermission::getPermission)
                .contains((short) 5);

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
