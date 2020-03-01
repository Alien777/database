package pl.lasota.tool.sr.service.security;


import pl.lasota.tool.sr.security.CreatableSecurity;

public interface CrudSecurityAction<CREATING extends CreatableSecurity, READING, UPDATING> {

    READING save(CREATING creating, @NotNull String user, @NotNull String group, Auth... specialAuths);

    READING get(Long id, String... privileges);

    Long delete(Long id, String... privileges);

    READING update(Long id, UPDATING updating, String... privileges);
}
