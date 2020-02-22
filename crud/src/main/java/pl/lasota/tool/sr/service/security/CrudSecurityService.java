package pl.lasota.tool.sr.service.security;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.security.*;
import pl.lasota.tool.sr.service.base.BaseCrudService;

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
    public READING save(CREATING creating, Context context) {
        fillObjectSecurity(creating, context);
        MODEL save = crudService.save(creating);
        return modelToReading.mapper(save);
    }

    public READING get(Long id, Context context) {
        MODEL reading = crudService.get(id);
        boolean b = canRead(reading, context);
        return b ? modelToReading.mapper(reading) : null;
    }

    @Transactional
    public Long delete(Long id, Context context) {
        MODEL reading = crudService.get(id);
        boolean b = canDelete(reading, context);
        return b ? crudService.delete(id) : null;
    }

    @Transactional
    public READING update(Long id, UPDATING updating, Context context) {
        MODEL reading = crudService.get(id);
        boolean b = canUpdate(reading, context);
        return b ? modelToReading.mapper(crudService.update(id, updating)) : null;
    }


    private void fillObjectSecurity(CreatableSecurity creatableSecurity, Context context) {
        Set<Access> accesses = context.getSecured().stream()
                .map(accessContext -> new Access(accessContext.getName(), accessContext.getRud()))
                .filter(access -> access.getRud() != 0)
                .collect(Collectors.toSet());

        creatableSecurity.setAccesses(accesses);
    }

    private boolean canRead(EntitySecurity objectSecurity, Context context) {
        Set<AccessContext> secureds = context.getSecured();
        Set<Access> accesses = objectSecurity.getAccesses();
        for (Access access : accesses) {
            for (AccessContext secured : secureds) {
                if (access.getName().equals(secured.getName())) {
                    short rud = access.getRud();
                    if (providingRules.forCanRead().contains(rud)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    private boolean canDelete(EntitySecurity objectSecurity, Context context) {
        Set<AccessContext> secureds = context.getSecured();
        Set<Access> accesses = objectSecurity.getAccesses();
        for (Access access : accesses) {
            for (AccessContext secured : secureds) {
                if (access.getName().equals(secured.getName())) {
                    short rud = access.getRud();
                    if (providingRules.forCanDelete().contains(rud)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean canUpdate(EntitySecurity objectSecurity, Context context) {
        Set<AccessContext> secureds = context.getSecured();
        Set<Access> accesses = objectSecurity.getAccesses();
        for (Access access : accesses) {
            for (AccessContext secured : secureds) {
                if (access.getName().equals(secured.getName())) {
                    short rud = access.getRud();
                    if (providingRules.forCanUpdate().contains(rud)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
