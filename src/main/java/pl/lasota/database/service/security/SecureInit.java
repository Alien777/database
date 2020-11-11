package pl.lasota.database.service.security;

import lombok.Getter;
import pl.lasota.database.security.SpecialPermission;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
public final class SecureInit {
    public final String owner;
    public final String group;
    public final List<SpecialPermission> privilege;

    public SecureInit(String owner) {
        this(owner, null, new LinkedList<>());
    }

    public SecureInit(String owner, List<SpecialPermission> privilege) {
        this(owner, null, privilege);
    }

    public SecureInit(String owner, String group) {
        this.owner = owner;
        this.group = group;
        this.privilege = null;
    }

    public SecureInit(String owner, String group, List<SpecialPermission> privilege) {

        this.owner = owner;
        this.group = group;
        this.privilege = privilege;
    }

    public SecureInit(String owner, SpecialPermission... privilege) {
        this.owner = owner;
        this.group = null;
        this.privilege = Arrays.asList(privilege);
    }

    public SecureInit(String owner, String group, SpecialPermission... privilege) {
        this.owner = owner;
        this.group = group;
        this.privilege = Arrays.asList(privilege);
    }
}
