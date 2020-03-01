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

import static org.assertj.core.api.Assertions.assertThat;

import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.base.Search;

import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SearchSecurityServiceTest {

    @Mock
    private Search<Entit> searchAction;

    private ProvidingPrivilege providingPrivilege;

    private SearchSecurity<Entit> searchSecurityService;

    @Before
    public void init() {
        providingPrivilege = new SimpleProvidingPrivileges();
        searchSecurityService = new SearchSecurity<Entit>(searchAction, providingPrivilege);
    }

    @Test
    public void findIfCorrectPrivilege() {

        List<Field<?>> fields = new LinkedList<>();
        searchSecurityService.find(fields, "admin");
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
                    assertThat(a.getValue()).contains("4", "5", "6", "7");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo(EntitySecurity.AUTHORIZATION_PERMISSION);
                });
    }

    @Test(expected = NullPointerException.class)
    public void findIfNotCorrectPrivilege() {
        List<Field<?>> fields = new LinkedList<>();
        searchSecurityService.find(fields, null);
    }
}
