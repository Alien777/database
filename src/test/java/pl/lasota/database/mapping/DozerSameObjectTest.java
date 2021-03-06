package pl.lasota.database.mapping;

import org.junit.Test;
import pl.lasota.database.helper.TestNotMapping;
import pl.lasota.database.helper.Entit;
import pl.lasota.database.helper.ObjectTest;
import pl.lasota.database.security.Entitlement;
import pl.lasota.database.security.SpecialPermission;

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
        Entitlement entitlement = new Entitlement();
        entitlement.setSpecialPermissions(toUpdateSpecialPermissions);
        toUpdate.setEntitlement(entitlement);


        Entit toOld = new Entit();
        toOld.setId(2L);
        toOld.setColor("STARY  KOLOR");
        HashSet<SpecialPermission> toOldSpecialPermissions = new HashSet<>();
        toOldSpecialPermissions.add(new SpecialPermission("stary_role", (short) 5));
        entitlement = new Entitlement();
        entitlement.setSpecialPermissions(toOldSpecialPermissions);
        toOld.setEntitlement(entitlement);


        DozerSameObject<Entit> object = new DozerSameObject<>(Entit.class);
        object.mapper(toUpdate, toOld);

        assertThat(toOld.getColor()).isEqualTo("NOWY KOLOR");
        assertThat(toOld.getId()).isEqualTo(2L);

        assertThat(toOld.getEntitlement().getSpecialPermissions())
                .hasSize(1)
                .extracting((Function<SpecialPermission, String>) SpecialPermission::getPrivileged)
                .contains("stary_role");

        assertThat(toOld.getEntitlement().getSpecialPermissions())
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
