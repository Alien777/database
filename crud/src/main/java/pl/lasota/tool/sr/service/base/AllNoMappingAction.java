package pl.lasota.tool.sr.service.base;

import org.springframework.data.domain.Page;
import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.RepositoryAdapter;
import pl.lasota.tool.sr.repository.query.QueryCriteria;
import pl.lasota.tool.sr.repository.query.QueryDelete;
import pl.lasota.tool.sr.repository.query.QueryUpdate;

import java.util.List;

public class AllNoMappingAction<MODEL extends BasicEntity>
        implements Crud<MODEL, MODEL, MODEL>,
        Delete, Search<MODEL>, Update {

    private final Search<MODEL> search;
    private final Update update;
    private final Crud<MODEL, MODEL, MODEL> crud;
    private final Delete delete;

    public AllNoMappingAction(RepositoryAdapter<MODEL> repositoryAdapter, Class<MODEL> modelClass) {
        this.search = new SearchNoMappingAction<>(repositoryAdapter, modelClass);
        update = new UpdateAction<>(repositoryAdapter, modelClass);
        crud = new CrudNoMappingAction<>(repositoryAdapter, modelClass);
        delete = new DeleteAction<>(repositoryAdapter, modelClass);
    }


    @Override
    public MODEL save(MODEL creating) {
        return crud.save(creating);
    }

    @Override
    public MODEL get(Long id) {
        return crud.get(id);
    }

    @Override
    public Long delete(Long id) {
        return crud.delete(id);
    }

    @Override
    public MODEL update(Long id, MODEL updating) {
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
    public Page<MODEL> find(QueryCriteria queryCriteria) {
        return search.find(queryCriteria);
    }
}
