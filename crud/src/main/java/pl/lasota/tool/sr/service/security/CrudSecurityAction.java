package pl.lasota.tool.sr.service.security;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.repository.crud.CrudRepository;
import pl.lasota.tool.sr.security.ProtectedEntity;
import pl.lasota.tool.sr.security.Entitlement;
import pl.lasota.tool.sr.security.SecurityProvider;
import pl.lasota.tool.sr.service.base.Crud;
import pl.lasota.tool.sr.service.base.CrudAction;

import java.util.HashSet;

@Transactional(readOnly = true)
public class CrudSecurityAction<CREATING extends SecurityProvider, READING, UPDATING, MODEL extends ProtectedEntity>
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
        throw new UnsupportedOperationException("This operation you can execute only on base Service");
    }

    @Transactional
    public READING save(CREATING creating, SecureInit secureInit) {
        Entitlement entitlement = new Entitlement();
        entitlement.setOwner(secureInit.getOwner());
        if (secureInit.getGroup() != null) {
            entitlement.setGroup(secureInit.getGroup());
        }

        if (secureInit.getPrivilege() != null) {
            entitlement.setSpecialPermissions(new HashSet<>(secureInit.getPrivilege()));
        }
        entitlement.setPermission((short) 1700);
        creating.setEntitlement(entitlement);

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


    private boolean canRead(ProtectedEntity objectSecurity) {
        return providingPrivilege.canRead(objectSecurity.getEntitlement(), providingContext.supply());
    }

    private boolean canDelete(ProtectedEntity objectSecurity) {
        return providingPrivilege.canDelete(objectSecurity.getEntitlement(), providingContext.supply());
    }

    private boolean canUpdate(ProtectedEntity objectSecurity) {
        return providingPrivilege.canUpdate(objectSecurity.getEntitlement(), providingContext.supply());
    }
}
