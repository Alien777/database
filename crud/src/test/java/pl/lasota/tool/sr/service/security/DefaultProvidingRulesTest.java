package pl.lasota.tool.sr.service.security;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultProvidingRulesTest {
    @Test
    public void testParser() {
        DefaultProvidingRules defaultProvidingRules = new DefaultProvidingRules();

        String test = defaultProvidingRules.create(Accessible::zero, "adam");
        assertThat(test).isEqualTo("adam___0");

        test = defaultProvidingRules.create(accessible -> accessible.read().delete().update(), "adam");
        assertThat(test).isEqualTo("adam___7");

        test = defaultProvidingRules.create(Accessible::delete, "adam");
        assertThat(test).isEqualTo("adam___1");

        test = defaultProvidingRules.create(accessible -> accessible.delete().update(), "adam");
        assertThat(test).isEqualTo("adam___3");

        test = defaultProvidingRules.create(accessible -> accessible.delete().update()
                .one().one().one().one().one().one().one(), "test");

        assertThat(test).isEqualTo("test___511");

    }

}
