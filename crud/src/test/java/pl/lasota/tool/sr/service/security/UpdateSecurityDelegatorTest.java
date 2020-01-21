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
import pl.lasota.tool.sr.security.Context;
import pl.lasota.tool.sr.service.base.BaseUpdateService;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UpdateSecurityDelegatorTest {

    @Mock
    private BaseUpdateService<Entit> baseUpdateService;

    private ProvidingRules providingRules;

    private UpdateSecurityDelegator<Entit> updateSecurityDelegator;

    @Before
    public void init() {
        providingRules = new DefaultProvidingRules();
        updateSecurityDelegator = new UpdateSecurityDelegator<>(baseUpdateService, providingRules);
    }


    @Test
    public void update() {

        Context context = new Context();
        context.add(providingRules.create( "admin"));

        List<Field<?>> fields = new LinkedList<>();
        updateSecurityDelegator.update(fields, context);
        assertThat(fields)
                .hasSize(1)
                .element(0)
                .isInstanceOfSatisfying(StringFields.class, a -> {
                    assertThat(a.condition()).isEqualByComparingTo(Operator.EQUALS);
                    assertThat(a.getValue()).containsAnyOf("admin___4", "admin___5", "admin___6", "admin___7");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo("accesses.value");
                });


        context = new Context();
        context.add(providingRules.create( null));

        fields = new LinkedList<>();
        updateSecurityDelegator.update(fields, context);
        assertThat(fields)
                .hasSize(1)
                .element(0)
                .isInstanceOfSatisfying(StringFields.class, a -> {
                    assertThat(a.condition()).isEqualByComparingTo(Operator.EQUALS);
                    assertThat(a.getValue()).containsAnyOf("null___4", "null___5", "null___6", "null___7");
                    assertThat(a.getSelector()).isEqualByComparingTo(Selector.AND);
                    assertThat(a.getName()).isEqualTo("accesses.value");
                });
    }
}
