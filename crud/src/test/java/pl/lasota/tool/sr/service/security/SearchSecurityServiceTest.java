package pl.lasota.tool.sr.service.security;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.lasota.tool.sr.field.definition.Field;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;
import pl.lasota.tool.sr.field.definition.MultipleValuesField;
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
        searchSecurityService = new SearchSecurity<Entit>(searchAction, providingPrivilege, new ProvidingContext() {
            @Override
            public Context supply() {
                LinkedList<String> strings = new LinkedList<>();
                strings.add("admin");
                return new Context("admin", "admin", strings);
            }
        });
    }

    @Test
    public void findIfCorrectPrivilege() {

        List<Field<?>> fields = new LinkedList<>();
        searchSecurityService.find(fields);
        assertThat(fields)
                .hasSize(2)
                .element(0)
                .isInstanceOfSatisfying(MultipleValuesField.class, a -> {
                    assertThat(a.condition()).isEqualByComparingTo(Operator.EQUALS);
                    assertThat(a.getValue()).contains("admin");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo(EntitySecurity.AUTHORIZATION_PRIVILEGED);
                });

        assertThat(fields)
                .hasSize(2)
                .element(1)
                .isInstanceOfSatisfying(MultipleValuesField.class, a -> {
                    assertThat(a.condition()).isEqualByComparingTo(Operator.EQUALS);
                    assertThat(a.getValue()).contains("4", "5", "6", "7");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo(EntitySecurity.AUTHORIZATION_PERMISSION);
                });
    }

    @Test(expected = NullPointerException.class)
    public void findIfNotCorrectPrivilege() {
        List<Field<?>> fields = new LinkedList<>();
    new SearchSecurity<Entit>(searchAction, providingPrivilege, () -> new Context(null, null, null)).find(fields);
    }
}
