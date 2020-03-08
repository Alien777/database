package pl.lasota.tool.sr.security;

import java.util.Set;


public interface Entitling {

    Set<SpecialPermission> getSpecialPermissions();

    void setSpecialPermissions(Set<SpecialPermission> specialPermissions);

    String getOwner();

    void setOwner(String owner);

    String getGroup();

    void setGroup(String group);

    Short getPermission();

    void setPermission(Short permission);
}
