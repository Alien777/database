package pl.lasota.tool.sr.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.field.definition.Field;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.EntityRepository;

import java.util.List;

public class AllAction<CREATING, READING, UPDATING, MODEL extends EntityBase>
        implements Crud<CREATING, READING, UPDATING>,
        Delete, Search<READING>, Update {

    private final Search<READING> search;
    private final Update update;
    private final Crud<CREATING, READING, UPDATING> crud;
    private final Delete delete;

    public AllAction(EntityRepository<MODEL> entityRepository,
                     Mapping<Page<MODEL>, Page<READING>> search,
                     Mapping<CREATING, MODEL> creatingToModel,
                     Mapping<UPDATING, MODEL> updatingToModel,
                     Mapping<MODEL, READING> modelToReading, Class<MODEL> modelClass) {

        this.search = new SearchAction<>(entityRepository, search, modelClass);
        update = new UpdateAction<>(entityRepository, modelClass);
        crud = new CrudAction<>(entityRepository, creatingToModel, updatingToModel, modelToReading, modelClass);
        delete = new DeleteAction<>(entityRepository, modelClass);
    }


    @Override
    public READING save(CREATING creating) {
        return crud.save(creating);
    }

    @Override
    public READING get(Long id) {
        return crud.get(id);
    }

    @Override
    public Long delete(Long id) {
        return crud.delete(id);
    }

    @Override
    public READING update(Long id, UPDATING updating) {
        return crud.update(id, updating);
    }

    @Override
    public List<Long> delete(List<Field<?>> source) {
        return delete.delete(source);
    }

    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        return search.find(source, pageable);
    }

    @Override
    public List<Long> update(List<Field<?>> source) {
        return update.update(source);
    }
}
