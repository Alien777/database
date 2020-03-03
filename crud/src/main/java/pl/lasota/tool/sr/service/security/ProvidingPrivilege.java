package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.security.CreatableSecurity;

public interface ProvidingPrivilege {

    Short[] read();

    Short[] delete();

    Short[] update();

    boolean canRead(CreatableSecurity creatableSecurity, Context context);

    boolean canUpdate(CreatableSecurity creatableSecurity, Context context);

    boolean canDelete(CreatableSecurity creatableSecurity, Context context);

    Short create(ConfigurationAccessible configurationAccessible);

    Short create(ConfigurationAccessibleChmod configurationAccessible);

}
