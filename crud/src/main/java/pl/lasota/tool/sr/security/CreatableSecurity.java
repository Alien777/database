package pl.lasota.tool.sr.security;

import java.util.Set;


public interface CreatableSecurity {

    Set<SpecialPermission> getSpecialPermission();

    String getUser();

    String getGroup();

    Short getPermission();

    void setUser(String user);

    void setGroup(String group);

    void setPermission(Short permission);
}
