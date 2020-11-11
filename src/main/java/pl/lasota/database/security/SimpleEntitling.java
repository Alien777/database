package pl.lasota.database.security;

import java.util.Set;


public class SimpleEntitling implements Entitling {

    private Set<SpecialPermission> specialPermissions;
    private String owner;
    private String group;
    private Short permission;

    public Set<SpecialPermission> getSpecialPermissions() {
        return specialPermissions;
    }

    public void setSpecialPermissions(Set<SpecialPermission> specialPermissions) {
        this.specialPermissions = specialPermissions;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Short getPermission() {
        return permission;
    }

    public void setPermission(Short permission) {
        this.permission = permission;
    }
}
