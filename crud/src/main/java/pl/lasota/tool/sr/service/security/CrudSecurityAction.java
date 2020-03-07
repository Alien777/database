package pl.lasota.tool.sr.service.security;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.repository.crud.CrudRepository;
import pl.lasota.tool.sr.security.CreatableSecurity;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.base.Crud;
import pl.lasota.tool.sr.service.base.CrudAction;

@Transactional(readOnly = true)
public class CrudSecurityAction<CREATING extends CreatableSecurity, READING, UPDATING, MODEL extends EntitySecurity>
        extends CrudAction<CREATING, READING, UPDATING, MODEL> {

    private final Crud<MODEL, MODEL, MODEL> helper;

    private final ProvidingContext providingContext;
    private final ProvidingPrivilege providingPrivilege;

    public CrudSecurityAction(CrudRepository<MODEL> repository,
                              Mapping<CREATING, MODEL> creatingToModel,
                              Mapping<UPDATING, MODEL> updatingToModel,
                              Mapping<MODEL, READING> modelToReading,
                              ProvidingContext providingContext,
                              ProvidingPrivilege providingPrivilege,
                              Class<MODEL> modelClass) {
        super(repository, creatingToModel, updatingToModel, modelToReading, modelClass);
        this.providingContext = providingContext;
        this.providingPrivilege = providingPrivilege;

        helper = new CrudAction<>(repository,
                new DozerMapper<>(modelClass),
                new DozerMapper<>(modelClass),
                new DozerMapper<>(modelClass),
                modelClass);
    }

    @Transactional
    @Override
    public READING save(CREATING creating) {
        Context context = providingContext.supply();
        creating.setOwner(context.getOwner());
        creating.setGroup(context.getGroup());
        creating.setPermission((short) 1700);
        return super.save(creating);
    }

    @Override
    public READING get(Long id) {
        MODEL reading = helper.get(id);
        boolean b = canRead(reading);
        return b ? super.get(id) : null;
    }

    @Transactional
    @Override
    public Long delete(Long id) {
        MODEL reading = helper.get(id);
        boolean b = canDelete(reading);
        return b ? super.delete(id) : null;
    }

    @Transactional
    @Override
    public READING update(Long id, UPDATING updating) {
        MODEL reading = helper.get(id);
        boolean b = canUpdate(reading);
        return b ? super.update(id, updating) : null;
    }


    private boolean canRead(EntitySecurity objectSecurity) {
        return providingPrivilege.canRead(objectSecurity, providingContext.supply());
    }

    private boolean canDelete(EntitySecurity objectSecurity) {
        return providingPrivilege.canDelete(objectSecurity, providingContext.supply());
    }

    private boolean canUpdate(EntitySecurity objectSecurity) {
        return providingPrivilege.canUpdate(objectSecurity, providingContext.supply());
    }
}
