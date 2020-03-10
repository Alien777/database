package pl.lasota.tool.sr.service.security;

import com.google.common.collect.ImmutableSet;
import pl.lasota.tool.sr.security.Entitling;

import java.util.LinkedList;
import java.util.List;

public class SimpleProvidingPrivileges implements ProvidingPrivilege {


    private final static ImmutableSet<Short> read = ImmutableSet.<Short>builder().add((short) 4).add((short) 5).add((short) 6).add((short) 7).build();
    private final static ImmutableSet<Short> update = ImmutableSet.<Short>builder().add((short) 2).add((short) 3).add((short) 6).add((short) 7).build();
    private final static ImmutableSet<Short> delete = ImmutableSet.<Short>builder().add((short) 1).add((short) 3).add((short) 5).add((short) 7).build();

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
    public boolean canRead(Entitling entitling, Context context) {
        return isFit(entitling, context, read);
    }

    @Override
    public boolean canUpdate(Entitling entitling, Context context) {
        return isFit(entitling, context, update);
    }

    @Override
    public boolean canDelete(Entitling entitling, Context context) {
        return isFit(entitling, context, delete);
    }

    @Override
    public Short create(ConfigurationAccessible configurationAccessible) {
        final List<Boolean> roles = new LinkedList<>();
        roles.add(false);
        roles.add(false);
        roles.add(false);
        configurationAccessible.accesses(createAccessible(roles));
        return createRud(roles);
    }

    @Override
    public Short create(ConfigurationAccessibleChmod configurationAccessible) {

        final List<Boolean> userRole = new LinkedList<>();
        userRole.add(false);
        userRole.add(false);
        userRole.add(false);
        configurationAccessible.owner(createAccessible(userRole));

        final List<Boolean> groupRole = new LinkedList<>();
        groupRole.add(false);
        groupRole.add(false);
        groupRole.add(false);
        configurationAccessible.group(createAccessible(groupRole));

        final List<Boolean> otherRole = new LinkedList<>();
        otherRole.add(false);
        otherRole.add(false);
        otherRole.add(false);
        configurationAccessible.other(createAccessible(otherRole));
        short userRud = createRud(userRole);
        short groupRud = createRud(groupRole);
        short otherRud = createRud(otherRole);
        if (userRud > 7 || groupRud > 7 || otherRud > 7) {
            throw new UnsupportedOperationException("Value >7 for user, group, other is too big");
        }
        return (short) (1000 + userRud * 100 + groupRud * 10 + otherRud);
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

    private short digit(int number, int position) {
        if (number == 0) return 0;
        String numberS = String.valueOf(number);
        return Short.parseShort(numberS.split("(?<=.)")[position]);
    }

    private boolean isFit(Entitling entitling, Context context, ImmutableSet<Short> permissions) {
        short u = digit(entitling.getPermission(), 1);
        short g = digit(entitling.getPermission(), 2);
        short o = digit(entitling.getPermission(), 3);
        if (context.getOwner().equals(entitling.getOwner()) && permissions.contains(u)) {
            return true;
        } else if (entitling.getGroup() != null
                && context.getGroup() != null
                && context.getGroup().equals(entitling.getGroup())
                && permissions.contains(g)) {
            return true;
        } else if (permissions.contains(o)) {
            return true;
        } else if (entitling.getSpecialPermissions() != null && !entitling.getSpecialPermissions().isEmpty()) {
            return entitling
                    .getSpecialPermissions()
                    .stream()
                    .anyMatch(sp -> context.getPrivilege().contains(sp.getPrivileged()) && permissions.contains(sp.getPermission()));
        }

        return false;
    }

    private static Accessible createAccessible(List<Boolean> roles) {
        return new Accessible() {
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
        };
    }
}
