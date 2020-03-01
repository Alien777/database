package pl.lasota.tool.sr.service.security;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SimpleProvidingPrivileges implements ProvidingPrivilege {


    private final ImmutableSet<Short> read = ImmutableSet.<Short>builder().add((short) 4).add((short) 5).add((short) 6).add((short) 7).build();
    private final ImmutableSet<Short> update = ImmutableSet.<Short>builder().add((short) 2).add((short) 3).add((short) 6).add((short) 7).build();
    private final ImmutableSet<Short> delete = ImmutableSet.<Short>builder().add((short) 1).add((short) 3).add((short) 5).add((short) 7).build();

    @Override
    public Short[] read() {
        return read.toArray(Short[]::new);
    }

    @Override
    public Short[] update() {
        return update.toArray(Short[]::new);
    }

    @Override
    public Short[] delete() {
        return delete.toArray(Short[]::new);
    }

    @Override
    public boolean canRead(short permission) {
        return read.contains(permission);
    }

    @Override
    public boolean canUpdate(short permission) {
        return update.contains(permission);
    }

    @Override
    public boolean canDelete(short permission) {
        return delete.contains(permission);
    }

    @Override
    public Short create(ConfigurationAccessible configurationAccessible) {
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

        return createRud(roles);
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
}
