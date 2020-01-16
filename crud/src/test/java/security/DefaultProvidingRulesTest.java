package security;

import org.junit.Test;
import pl.lasota.tool.sr.security.AccessContext;
import pl.lasota.tool.sr.service.security.Accessible;
import pl.lasota.tool.sr.service.security.DefaultProvidingRules;


public class DefaultProvidingRulesTest {
    @Test
    public void testParser() {
        DefaultProvidingRules defaultProvidingRules = new DefaultProvidingRules();

        AccessContext test = defaultProvidingRules.create(Accessible::zero, "adam");
        assert test.getRud() == 0 && test.getName().equals("adam");

        test = defaultProvidingRules.create(accessible -> accessible.read().delete().update(), "adam");
        assert test.getRud() == 7 && test.getName().equals("adam");

        test = defaultProvidingRules.create(Accessible::delete, "adam");
        assert test.getRud() == 1 && test.getName().equals("adam");

        test = defaultProvidingRules.create(accessible -> accessible.delete().update(), "test");
        assert test.getRud() == 3 && test.getName().equals("test");

        test = defaultProvidingRules.create(accessible -> accessible.delete().update()
                .one().one().one().one().one().one().one(), "test");

        System.out.println(test.getRud());
        assert test.getRud() == 511 && test.getName().equals("test");

    }

}
