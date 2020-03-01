package pl.lasota.tool.sr.service.security;

import java.util.*;

public interface ProvidingPrivilege {

    Short[] read();

    Short[] delete();

    Short[] update();

    boolean canRead(short permissions);

    boolean canUpdate(short permissions);

    boolean canDelete(short permissions);

    Short create(ConfigurationAccessible configurationAccessible);

}
