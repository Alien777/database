package pl.lasota.tool.sr.security;

import java.util.Set;


public abstract class CreatorSecurity implements CreatableSecurity {

    private Set<SpecialPermission> specialPermissions;

    private String user;
    private String group;
    private short permission;


    public Set<SpecialPermission> getSpecialPermissions() {
        return specialPermissions;
    }

    public void setSpecialPermissions(Set<SpecialPermission> specialPermissions) {
        this.specialPermissions = specialPermissions;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public short getPermission() {
        return permission;
    }

    public void setPermission(short permission) {
        this.permission = permission;
    }
}
