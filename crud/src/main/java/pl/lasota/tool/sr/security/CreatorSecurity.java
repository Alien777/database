package pl.lasota.tool.sr.security;

import java.util.Set;


public class CreatorSecurity implements CreatableSecurity {

    private Set<SpecialPermission> specialPermissions;
    private String user;
    private String group;
    private Short permission;

    public Set<SpecialPermission> getSpecialPermissions() {
        return specialPermissions;
    }

    public void setSpecialPermissions(Set<SpecialPermission> specialPermissions) {
        this.specialPermissions = specialPermissions;
    }

    public String getOwner() {
        return user;
    }

    public void setOwner(String owner) {
        this.user = owner;
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
