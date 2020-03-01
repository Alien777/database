package pl.lasota.tool.sr.service.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;
import pl.lasota.tool.sr.field.StringFields;
import pl.lasota.tool.sr.helper.Entit;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.base.UpdateAction;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UpdateSecurityServiceTest {

    @Mock
    private UpdateAction<Entit> updateAction;

    private ProvidingPrivilege providingPrivilege;

    private UpdateSecurity updateSecurityService;

    @Before
    public void init() {
        providingPrivilege = new SimpleProvidingPrivileges();
        updateSecurityService = new UpdateSecurity(updateAction, providingPrivilege);
    }


    @Test
    public void updateIfCorrectPrivilege() {

        List<Field<?>> fields = new LinkedList<>();
        updateSecurityService.update(fields, "admin");
        assertThat(fields)
                .hasSize(2)
                .element(0)
                .isInstanceOfSatisfying(StringFields.class, a -> {
                    assertThat(a.condition()).isEqualByComparingTo(Operator.EQUALS);
                    assertThat(a.getValue()).contains("admin");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo(EntitySecurity.AUTHORIZATION_PRIVILEGED);
                });

        assertThat(fields)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(StringFields.class, a -> {
                    assertThat(a.condition()).isEqualByComparingTo(Operator.EQUALS);
                    assertThat(a.getValue()).contains("2", "3", "6", "7");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo(EntitySecurity.AUTHORIZATION_PERMISSION);
                });
    }

    @Test(expected = NullPointerException.class)
    public void updateIfNotCorrectPrivilege() {
        List<Field<?>> fields = new LinkedList<>();
        updateSecurityService.update(fields, null);
    }
}
