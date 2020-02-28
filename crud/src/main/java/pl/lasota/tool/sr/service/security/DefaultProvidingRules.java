package pl.lasota.tool.sr.service.security;

import com.google.common.collect.ImmutableSet;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DefaultProvidingRules implements ProvidingRules {

    public static final String SEPARATOR = "___";

    private final ImmutableSet<Short> read = ImmutableSet.<Short>builder().add((short) 4).add((short) 5).add((short) 6).add((short) 7).build();
    private final ImmutableSet<Short> update = ImmutableSet.<Short>builder().add((short) 2).add((short) 3).add((short) 6).add((short) 7).build();
    private final ImmutableSet<Short> delete = ImmutableSet.<Short>builder().add((short) 1).add((short) 3).add((short) 5).add((short) 7).build();


    @Override
    public boolean canRead(String privilegeRud) {
        short s = Short.parseShort(privilegeRud.split(SEPARATOR)[1]);
        return read.contains(s);
    }

    @Override
    public boolean canUpdate(String privilegeRud) {
        short s = Short.parseShort(privilegeRud.split(SEPARATOR)[1]);
        return update.contains(s);
    }

    @Override
    public boolean canDelete(String privilegeRud) {
        short s = Short.parseShort(privilegeRud.split(SEPARATOR)[1]);
        return delete.contains(s);
    }

    @Override
    public boolean canRead(short rud) {
        return read.contains(rud);
    }

    @Override
    public boolean canUpdate(short rud) {
        return update.contains(rud);
    }

    @Override
    public boolean canDelete(short rud) {
        return delete.contains(rud);
    }

    @Override
    public List<String> createAllReadPrivilegeRud(String privilege) {
        return read.stream().map(r -> privilege + SEPARATOR + r).collect(Collectors.toList());
    }

    @Override
    public List<String> createAllDeletePrivilegeRud(String privilege) {
        return delete.stream().map(r -> privilege + SEPARATOR + r).collect(Collectors.toList());
    }

    @Override
    public List<String> createAllUpdatePrivilegeRud(String privilege) {
        return update.stream().map(r -> privilege + SEPARATOR + r).collect(Collectors.toList());
    }

    @Override
    public boolean hasAccess(String privilegeRud, short rud) {
        return Short.parseShort(privilegeRud.split(SEPARATOR)[1]) == rud;
    }

    @Override
    public String create(ConfigurationAccessible configurationAccessible, String privilege) {
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
        return privilege + SEPARATOR + rud;
    }

    @Override
    public String create(String privilege, short rud) {
        return privilege + SEPARATOR + rud;
    }

    @Override
    public short rud(String privilegeRud) {
        return Short.parseShort(privilegeRud.split(SEPARATOR)[1]);
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
