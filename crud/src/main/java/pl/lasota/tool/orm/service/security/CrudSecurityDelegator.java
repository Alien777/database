package pl.lasota.tool.orm.service.security;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.orm.common.Access;
import pl.lasota.tool.orm.common.EntitySecurity;
import pl.lasota.tool.orm.security.*;
import pl.lasota.tool.orm.service.base.BaseCrudService;

import java.util.Set;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public class CrudSecurityDelegator<CREATING extends ObjectSecurity,
        READING extends ObjectSecurity, UPDATING extends UpdatingSecurity,
        MODEL extends EntitySecurity> {

    private final BaseCrudService<CREATING, READING, UPDATING, MODEL> crudService;

    public CrudSecurityDelegator(BaseCrudService<CREATING, READING, UPDATING, MODEL> crudService) {
        this.crudService = crudService;
    }

    @Transactional
    public READING save(CREATING creating, Context context) {
        fillObjectSecurity(creating, context);
        return crudService.save(creating);
    }

    public READING get(Long id, Context context) {
        READING reading = crudService.get(id);
        boolean b = canRead(reading, context);
        return b ? reading : null;
    }

    @Transactional
    public Long delete(Long id, Context context) {
        READING reading = crudService.get(id);
        boolean b = canDelete(reading, context);
        return b ? crudService.delete(id) : null;
    }

    @Transactional
    public READING update(UPDATING updating, Context context) {
        READING reading = crudService.get(updating.getId());
        boolean b = canUpdate(reading, context);
        return b ? crudService.update(updating) : null;
    }


    private void fillObjectSecurity(ObjectSecurity objectSecurity, Context context) {
        Set<Access> accesses = context.getSecured().stream()
                .map(accessContext -> new Access(accessContext.getName(), accessContext.getRud()))
                .filter(access -> access.getRud() != 0)
                .collect(Collectors.toSet());

        objectSecurity.setAccesses(accesses);
    }

    private boolean canRead(ObjectSecurity objectSecurity, Context context) {
        Set<AccessContext> secureds = context.getSecured();
        Set<Access> accesses = objectSecurity.getAccesses();
        for (Access access : accesses) {
            for (AccessContext secured : secureds) {
                if (access.getName().equals(secured.getName())) {
                    short rud = access.getRud();
                    if (rud == 4 || rud == 6 || rud == 5 || rud == 7) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean canDelete(ObjectSecurity objectSecurity, Context context) {
        Set<AccessContext> secureds = context.getSecured();
        Set<Access> accesses = objectSecurity.getAccesses();
        for (Access access : accesses) {
            for (AccessContext secured : secureds) {
                if (access.getName().equals(secured.getName())) {
                    short rud = access.getRud();
                    if (rud == 1 || rud == 3 || rud == 5 || rud == 7) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canUpdate(ObjectSecurity objectSecurity, Context context) {
        Set<AccessContext> secureds = context.getSecured();
        Set<Access> accesses = objectSecurity.getAccesses();
        for (Access access : accesses) {
            for (AccessContext secured : secureds) {
                if (access.getName().equals(secured.getName())) {
                    short rud = access.getRud();
                    if (rud == 2 || rud == 3 || rud == 6 || rud == 7) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
