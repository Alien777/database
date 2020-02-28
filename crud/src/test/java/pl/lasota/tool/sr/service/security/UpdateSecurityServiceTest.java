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
import pl.lasota.tool.sr.service.base.BaseUpdateService;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UpdateSecurityServiceTest {

    @Mock
    private BaseUpdateService<Entit> baseUpdateService;

    private ProvidingRules providingRules;

    private UpdateSecurityService<Entit> updateSecurityService;

    @Before
    public void init() {
        providingRules = new DefaultProvidingRules();
        updateSecurityService = new UpdateSecurityService<>(baseUpdateService, providingRules);
    }


    @Test
    public void updateIfCorrectPrivilege() {

        List<Field<?>> fields = new LinkedList<>();
        updateSecurityService.update(fields, "admin");
        assertThat(fields)
                .hasSize(1)
                .element(0)
                .isInstanceOfSatisfying(StringFields.class, a -> {
                    assertThat(a.condition()).isEqualByComparingTo(Operator.EQUALS);
                    assertThat(a.getValue()).contains("admin___2", "admin___3", "admin___6", "admin___7");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo(EntitySecurity.FIELD_SECURED);
                });
    }

    @Test(expected = NullPointerException.class)
    public void updateIfNotCorrectPrivilege() {
        List<Field<?>> fields = new LinkedList<>();
        updateSecurityService.update(fields, null);
    }
}
