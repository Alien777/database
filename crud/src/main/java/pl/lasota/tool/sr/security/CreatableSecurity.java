package pl.lasota.tool.sr.security;

import java.util.Set;


public interface CreatableSecurity {

    Set<SpecialPermission> getSpecialPermission();

    void setSpecialPermission(Set<SpecialPermission> specialPermission);

    String getUser();

    void setUser(String user);

    String getGroup();

    void setGroup(String group);

    short getPermission();

    void setPermission(short permission);
}
