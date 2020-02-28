package pl.lasota.tool.sr.service.security;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.security.*;
import pl.lasota.tool.sr.service.base.BaseCrudService;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public class CrudSecurityService<CREATING extends CreatableSecurity, READING, UPDATING, MODEL extends EntitySecurity> {

    private final BaseCrudService<CREATING, MODEL, UPDATING, MODEL> crudService;

    private DozerMapper<MODEL, READING> modelToReading;
    private final ProvidingRules providingRules;

    public CrudSecurityService(BaseCrudService<CREATING, MODEL, UPDATING, MODEL> crudService, DozerMapper<MODEL, READING> modelToReading, ProvidingRules providingRules) {
        this.crudService = crudService;
        this.modelToReading = modelToReading;
        this.providingRules = providingRules;
    }

    @Transactional
    public READING save(CREATING creating, String... withPrivilegesRud) {
        fillObjectSecurity(creating, withPrivilegesRud);
        MODEL save = crudService.save(creating);
        return modelToReading.mapper(save);
    }

    public READING get(Long id, String... privileges) {
        MODEL reading = crudService.get(id);
        boolean b = canRead(reading, privileges);
        return b ? modelToReading.mapper(reading) : null;
    }

    @Transactional
    public Long delete(Long id, String... privileges) {
        MODEL reading = crudService.get(id);
        boolean b = canDelete(reading, privileges);
        return b ? crudService.delete(id) : null;
    }

    @Transactional
    public READING update(Long id, UPDATING updating, String... privileges) {
        MODEL reading = crudService.get(id);
        boolean b = canUpdate(reading, privileges);
        return b ? modelToReading.mapper(crudService.update(id, updating)) : null;
    }


    private void fillObjectSecurity(CreatableSecurity creatableSecurity, String... withPrivilegesRud) {
        Set<Access> withAccesses = Arrays.stream(withPrivilegesRud)
                .map(s -> {
                    short rud = providingRules.rud(s);
                    return new Access(s, rud, providingRules.create(s, rud));
                })
                .collect(Collectors.toSet());

        creatableSecurity.setAccesses(withAccesses);
    }

    private boolean canRead(EntitySecurity objectSecurity, String... privileges) {
        for (Access access : objectSecurity.getAccesses()) {
            for (String secured : privileges) {
                if (access.getPrivilege().equals(secured)) {
                    return providingRules.canRead(access.getRud());
                }
            }
        }
        return false;
    }

    private boolean canDelete(EntitySecurity objectSecurity, String... privileges) {
        for (Access access : objectSecurity.getAccesses()) {
            for (String secured : privileges) {
                if (access.getPrivilege().equals(secured)) {
                    return providingRules.canDelete(access.getRud());
                }
            }
        }
        return false;
    }

    private boolean canUpdate(EntitySecurity objectSecurity, String... privileges) {
        for (Access access : objectSecurity.getAccesses()) {
            for (String secured : privileges) {
                if (access.getPrivilege().equals(secured)) {
                    return providingRules.canUpdate(access.getRud());
                }
            }
        }
        return false;
    }
}
