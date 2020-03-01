package pl.lasota.tool.sr.service.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.mapping.DozerMapper;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.repository.EntityRepository;
import pl.lasota.tool.sr.security.CreatableSecurity;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.base.CrudAction;
import pl.lasota.tool.sr.service.base.DeleteAction;
import pl.lasota.tool.sr.service.base.SearchAction;
import pl.lasota.tool.sr.service.base.UpdateAction;

import java.util.List;

public class AllSecurityAction<CREATING extends CreatableSecurity, READING, UPDATING, MODEL extends EntitySecurity>
        implements CrudSecurityAction<CREATING, READING, UPDATING>,
        UpdateSecurityAction,
        SearchSecurityAction<READING>,
        DeleteSecurityAction {

    private final SearchSecurityAction<READING> searchSecureService;
    private final DeleteSecurityAction deleteSecureService;
    private final UpdateSecurityAction updateSecureService;
    private final CrudSecurityAction<CREATING, READING, UPDATING> crudSecureService;

    public AllSecurityAction(EntityRepository<MODEL> entityRepository,
                             Mapping<Page<MODEL>, Page<READING>> search,
                             Mapping<CREATING, MODEL> creatingToModel,
                             Mapping<UPDATING, MODEL> updatingToModel,
                             Mapping<MODEL, READING> modelToReading,
                             Class<MODEL> modelClass,
                             ProvidingPrivilege providingPrivilege) {


        searchSecureService = new SearchSecurity<>(new SearchAction<>(entityRepository, search, modelClass), providingPrivilege);
        deleteSecureService = new DeleteSecurity(new DeleteAction<>(entityRepository, modelClass), providingPrivilege);
        updateSecureService = new UpdateSecurity(new UpdateAction<>(entityRepository, modelClass), providingPrivilege);
        crudSecureService = new CrudSecurity<>(new CrudAction<>(entityRepository, creatingToModel, updatingToModel,
                new DozerMapper<>(modelClass), modelClass),
                modelToReading, providingPrivilege);
    }

    @Override
    public READING save(CREATING creating, String user, String group, Auth... specialAuths) {
        return null;
    }

    @Override
    public READING get(Long id, String user, String group, String... privileges) {
        return null;
    }

    @Override
    public Long delete(Long id, String user, String group, String... privileges) {
        return null;
    }

    @Override
    public READING update(Long id, UPDATING updating, String user, String group, String... privileges) {
        return null;
    }

    @Override
    public READING save(CREATING creating, String user, String group) {
        return null;
    }

    @Override
    public READING get(Long id, String user, String group) {
        return null;
    }

    @Override
    public Long delete(Long id, String user, String group) {
        return null;
    }

    @Override
    public READING update(Long id, UPDATING updating, String user, String group) {
        return null;
    }

    @Override
    public READING save(CREATING creating, String user) {
        return null;
    }

    @Override
    public READING get(Long id, String user) {
        return null;
    }

    @Override
    public Long delete(Long id, String user) {
        return null;
    }

    @Override
    public READING update(Long id, UPDATING updating, String user) {
        return null;
    }

    @Override
    public READING save(CREATING creating, Auth... specialAuths) {
        return crudSecureService.save(creating, specialAuths);
    }

    @Override
    public READING get(Long id, String... privileges) {
        return crudSecureService.get(id, privileges);
    }

    @Override
    public Long delete(Long id, String... privileges) {
        return crudSecureService.delete(id, privileges);
    }

    @Override
    public READING update(Long id, UPDATING updating, String... privileges) {
        return crudSecureService.update(id, updating, privileges);
    }

    @Override
    public List<Long> delete(List<Field<?>> source, String... privileges) {
        return deleteSecureService.delete(source, privileges);
    }

    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable, String... privilege) {
        return searchSecureService.find(source, pageable, privilege);
    }

    @Override
    public List<Long> update(List<Field<?>> source, String... privileges) {
        return updateSecureService.update(source, privileges);
    }
}
