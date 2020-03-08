package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.security.Entitling;

public interface ProvidingPrivilege {

    Short[] read();

    Short[] delete();

    Short[] update();

    boolean canRead(Entitling entitling, Context context);

    boolean canUpdate(Entitling entitling, Context context);

    boolean canDelete(Entitling entitling, Context context);

    Short create(ConfigurationAccessible configurationAccessible);

    Short create(ConfigurationAccessibleChmod configurationAccessible);

}
