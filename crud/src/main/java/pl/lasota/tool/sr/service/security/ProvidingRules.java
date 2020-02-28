package pl.lasota.tool.sr.service.security;

import java.util.*;

public interface ProvidingRules {

    boolean canRead(String privilegeRud);

    boolean canUpdate(String privilegeRud);

    boolean canDelete(String privilegeRud);

    boolean canRead(short rud);

    boolean canUpdate(short rud);

    boolean canDelete(short rud);

    List<String> createAllReadPrivilegeRud(String privilege);

    List<String> createAllDeletePrivilegeRud(String privilege);

    List<String> createAllUpdatePrivilegeRud(String privilege);

    String create(ConfigurationAccessible configurationAccessible, String privilege);

    String create(String privilege, short rud);

    boolean hasAccess(String privilegeRud, short rud);

    short rud(String privilegeRud);

}
