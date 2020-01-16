package pl.lasota.tool.sr.service.security;

import com.google.common.collect.ImmutableSet;
import pl.lasota.tool.sr.security.AccessContext;

import java.util.LinkedList;
import java.util.List;

public class DefaultProvidingRules implements ProvidingRules {

    private final ImmutableSet<Short> read = ImmutableSet.<Short>builder().add((short) 4).add((short) 5).add((short) 6).add((short) 7).build();
    private final ImmutableSet<Short> update = ImmutableSet.<Short>builder().add((short) 2).add((short) 3).add((short) 6).add((short) 7).build();
    private final ImmutableSet<Short> delete = ImmutableSet.<Short>builder().add((short) 1).add((short) 3).add((short) 5).add((short) 7).build();


    public ImmutableSet<Short> forCanRead() {
        return read;
    }

    public ImmutableSet<Short> forCanUpdate() {
        return update;
    }

    public ImmutableSet<Short> forCanDelete() {
        return delete;
    }

    public AccessContext create(ConfigurationAccessible configurationAccessible, String name) {
        final List<Boolean> roles = new LinkedList<>();
        roles.add(false);
        roles.add(false);
        roles.add(false);
        configurationAccessible.accesses(new Accessible() {
            public Accessible read() {
                roles.set(0, true);
                return this;
            }

            public Accessible update() {
                roles.set(1, true);
                return this;
            }

            public Accessible delete() {
                roles.set(2, true);
                return this;
            }

            public Accessible one() {
                roles.add(true);
                return this;
            }

            public Accessible zero() {
                roles.add(false);
                return this;
            }
        });

        short rud = createRud(roles);
        return new AccessContext(name, rud);
    }

    private short createRud(List<Boolean> roles) {
        int index = roles.size() - 1;
        double value = 0;
        for (Boolean b : roles) {
            if (b) value = value + Math.pow(2, index);
            index--;
        }
        return (short) value;
    }

    public AccessContext create(String name) {
        return new AccessContext(name);
    }


}
