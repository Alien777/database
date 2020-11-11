package pl.lasota.database.service.security;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleProvidingPrivilegesTest {
    @Test
    public void testParser() {
        SimpleProvidingPrivileges simpleProvidingPrivileges = new SimpleProvidingPrivileges();

        Short test = simpleProvidingPrivileges.create(Accessible::zero);
        assertThat(test).isEqualTo((short) 0);

        test = simpleProvidingPrivileges.create(accessible -> accessible.read().delete().update());
        assertThat(test).isEqualTo((short) 7);

        test = simpleProvidingPrivileges.create(Accessible::delete);
        assertThat(test).isEqualTo((short) 1);

        test = simpleProvidingPrivileges.create(accessible -> accessible.delete().update());
        assertThat(test).isEqualTo((short) 3);

        test = simpleProvidingPrivileges.create(accessible -> accessible.delete().update()
                .one().one().one().one().one().one().one());

        assertThat(test).isEqualTo((short) 511);

        assertThat(simpleProvidingPrivileges.create(new ConfigurationAccessibleChmod() {
            @Override
            public void owner(Accessible accessible) {
                accessible.read().delete().update();
            }

            @Override
            public void group(Accessible accessible) {
                accessible.read().delete().update();
            }

            @Override
            public void other(Accessible accessible) {
                accessible.read().delete().update();
            }
        })).isEqualTo((short) 1777);



        assertThat(simpleProvidingPrivileges.create(new ConfigurationAccessibleChmod() {
            @Override
            public void owner(Accessible accessible) {

            }

            @Override
            public void group(Accessible accessible) {
                accessible.read();
            }

            @Override
            public void other(Accessible accessible) {
                accessible.read();
            }
        })).isEqualTo((short) 1044);

    }

}
