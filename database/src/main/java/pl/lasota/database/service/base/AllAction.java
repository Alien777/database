package pl.lasota.database.service.base;

import org.springframework.data.domain.Page;
import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.RepositoryAdapter;
import pl.lasota.database.repository.query.QueryCriteria;
import pl.lasota.database.repository.query.QueryDelete;
import pl.lasota.database.repository.query.QueryUpdate;
import pl.lasota.database.mapping.Mapping;

import java.util.List;

public class AllAction<CREATING, READING, UPDATING, MODEL extends BasicEntity>
        implements Crud<CREATING, READING, UPDATING>,
        Delete, Search<READING>, Update {

    private final Search<READING> search;
    private final Update update;
    private final Crud<CREATING, READING, UPDATING> crud;
    private final Delete delete;

    public AllAction(RepositoryAdapter<MODEL> repositoryAdapter,
                     Mapping<Page<MODEL>, Page<READING>> search,
                     Mapping<CREATING, MODEL> creatingToModel,
                     Mapping<UPDATING, MODEL> updatingToModel,
                     Mapping<MODEL, READING> modelToReading, Class<MODEL> modelClass) {

        this.search = new SearchAction<>(repositoryAdapter, search, modelClass);
        update = new UpdateAction<>(repositoryAdapter, modelClass);
        crud = new CrudAction<>(repositoryAdapter, creatingToModel, updatingToModel, modelToReading, modelClass);
        delete = new DeleteAction<>(repositoryAdapter, modelClass);
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
    public List<Long> delete(QueryDelete source) {
        return delete.delete(source);
    }

    @Override
    public List<Long> update(QueryUpdate source) {
        return update.update(source);
    }

    @Override
    public Page<READING> find(QueryCriteria queryCriteria) {
        return search.find(queryCriteria);
    }

    @Override
    public Page<READING> findCount(QueryCriteria queryCriteria) {
        return search.findCount(queryCriteria);
    }
}
