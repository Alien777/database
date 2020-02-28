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
import pl.lasota.tool.sr.service.base.BaseSearchService;

import java.util.LinkedList;
import java.util.List;

@RunWith(MockitoJUnitRunner.Silent.class)
public class SearchSecurityServiceTest {

    @Mock
    private BaseSearchService<Entit, Entit> baseSearchService;

    private ProvidingRules providingRules;

    private SearchSecurityService<Entit, Entit> searchSecurityService;

    @Before
    public void init() {
        providingRules = new DefaultProvidingRules();
        searchSecurityService = new SearchSecurityService<>(baseSearchService, providingRules);
    }

    @Test
    public void findIfCorrectPrivilege() {

        List<Field<?>> fields = new LinkedList<>();
        searchSecurityService.find(fields, "admin");
        assertThat(fields)
                .hasSize(1)
                .element(0)
                .isInstanceOfSatisfying(StringFields.class, a -> {
                    assertThat(a.condition()).isEqualByComparingTo(Operator.EQUALS);
                    assertThat(a.getValue()).contains("admin___4", "admin___5", "admin___6", "admin___7");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo(EntitySecurity.FIELD_SECURED);
                });
    }

    @Test(expected = NullPointerException.class)
    public void findIfNotCorrectPrivilege() {
        List<Field<?>> fields = new LinkedList<>();
        searchSecurityService.find(fields, null);
    }
}
