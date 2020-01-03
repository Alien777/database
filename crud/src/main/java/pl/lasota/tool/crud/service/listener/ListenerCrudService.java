package pl.lasota.tool.crud.service.listener;

import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.crud.CrudRepository;
import pl.lasota.tool.crud.service.base.BaseCrudService;

import java.util.LinkedList;
import java.util.List;

public class ListenerCrudService<CREATING, READING, UPDATING, MODEL extends EntityBase>
        extends BaseCrudService<CREATING, READING, UPDATING, MODEL> implements ListenerService<Object> {

    private final List<ChangeListener<Object>> changeListeners = new LinkedList<>();

    public ListenerCrudService(CrudRepository<MODEL> repository, Mapping<CREATING, MODEL> creatingToModel,
                               Mapping<UPDATING, MODEL> updatingToModel, Mapping<MODEL, READING> modelToReading) {
        super(repository, creatingToModel, updatingToModel, modelToReading);
    }

    @Override
    public READING save(CREATING create) {
        READING save = super.save(create);
        changeListeners.forEach(l -> l.onChange(save, TypeListener.SAVE));
        return save;
    }

    @Override
    public READING get(Long id) {
        return super.get(id);
    }

    @Override
    public Long delete(Long id) {
        Long delete = super.delete(id);
        changeListeners.forEach(l -> l.onChange(delete, TypeListener.DELETE));
        return delete;
    }

    @Override
    public READING update(UPDATING updating) {
        READING update = super.update(updating);
        changeListeners.forEach(l -> l.onChange(update, TypeListener.UPDATE));
        return update;
    }

    @Override
    public void add(ChangeListener<Object> change) {
        if (change == null) {
            return;
        }
        changeListeners.add(change);
    }

    @Override
    public void remove(ChangeListener<Object> change) {
        changeListeners.remove(change);
    }


}
