package pl.lasota.tool.sr.service.security;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.security.*;
import pl.lasota.tool.sr.service.base.Crud;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public class CrudSecurity<CREATING extends CreatableSecurity, READING, UPDATING, MODEL extends EntitySecurity>
        implements CrudSecurityAction<CREATING, READING, UPDATING> {

    private final Crud<CREATING, MODEL, UPDATING> crud;

    private Mapping<MODEL, READING> modelToReading;
    private final ProvidingPrivilege providingPrivilege;

    public CrudSecurity(Crud<CREATING, MODEL, UPDATING> crud,
                        Mapping<MODEL, READING> modelToReading, ProvidingPrivilege providingPrivilege) {
        this.crud = crud;
        this.modelToReading = modelToReading;
        this.providingPrivilege = providingPrivilege;
    }

    @Transactional
    @Override
    public READING save(CREATING creating, String user, String group, Auth... specialAuths) {
        fillObjectSecurity(creating, user, group, specialAuths);
        MODEL save = crud.save(creating);
        return modelToReading.mapper(save);
    }

    @Override
    public READING get(Long id, String user, String group, String... privileges) {
        MODEL reading = crud.get(id);
        boolean b = canRead(reading, user, group, privileges);
        return b ? modelToReading.mapper(reading) : null;
    }

    @Transactional
    @Override
    public Long delete(Long id, String user, String group, String... privileges) {
        MODEL reading = crud.get(id);
        boolean b = canDelete(reading, user, group, privileges);
        return b ? crud.delete(id) : null;
    }

    @Transactional
    @Override
    public READING update(Long id, UPDATING updating, String user, String group, String... privileges) {
        MODEL reading = crud.get(id);
        boolean b = canUpdate(reading, user, group, privileges);
        return b ? modelToReading.mapper(crud.update(id, updating)) : null;
    }


    private void fillObjectSecurity(CreatableSecurity creatableSecurity, String user, String group, Auth... auths) {
        Set<SpecialPermission> withAuths = Arrays.stream(auths)
                .map(auth -> new SpecialPermission(auth.getPrivileged(), auth.getPermission()))
                .collect(Collectors.toSet());
        creatableSecurity.setGroup(group);
        creatableSecurity.setUser(user);
        creatableSecurity.setPermission((short) 700);
        creatableSecurity.setSpecialPermission(withAuths);
    }

    private boolean canRead(EntitySecurity objectSecurity, String... privileges) {
        for (SpecialPermission auth : objectSecurity.getSpecialPermission()) {
            for (String secured : privileges) {
                if (auth.getPrivileged().equals(secured)) {
                    return providingPrivilege.canRead(auth.getPermission());
                }
            }
        }
        return false;
    }

    private boolean canDelete(EntitySecurity objectSecurity, String... privileges) {
        for (SpecialPermission auth : objectSecurity.getSpecialPermission()) {
            for (String secured : privileges) {
                if (auth.getPrivileged().equals(secured)) {
                    return providingPrivilege.canDelete(auth.getPermission());
                }
            }
        }
        return false;
    }

    private boolean canUpdate(EntitySecurity objectSecurity, String... privileges) {
        for (SpecialPermission auth : objectSecurity.getSpecialPermission()) {
            for (String secured : privileges) {
                if (auth.getPrivileged().equals(secured)) {
                    return providingPrivilege.canUpdate(auth.getPermission());
                }
            }
        }
        return false;
    }
}
