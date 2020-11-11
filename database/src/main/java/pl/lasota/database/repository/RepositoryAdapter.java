package pl.lasota.database.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.database.repository.crud.CrudRepository;
import pl.lasota.database.repository.crud.SimpleCrudRepository;
import pl.lasota.database.repository.delete.DeleteRepository;
import pl.lasota.database.repository.delete.SimpleDeleteRepository;
import pl.lasota.database.repository.delete.specification.SpecificationDelete;
import pl.lasota.database.repository.search.SearchRepository;
import pl.lasota.database.repository.search.SimpleSearchRepository;
import pl.lasota.database.repository.search.specification.SpecificationQuery;
import pl.lasota.database.repository.update.SimpleUpdateRepository;
import pl.lasota.database.repository.update.specification.SpecificationUpdate;
import pl.lasota.database.repository.update.UpdateRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class RepositoryAdapter<MODEL extends BasicEntity>
        implements CrudRepository<MODEL>,
        SearchRepository<MODEL>,
        UpdateRepository<MODEL>,
        DeleteRepository<MODEL> {
    private static final ThreadLocal<EntityManager> entityManagerThreadLocal = new ThreadLocal<>();
    private final DeleteRepository<MODEL> deleteRepository;
    private final SearchRepository<MODEL> searchRepository;
    private final CrudRepository<MODEL> crudRepository;
    private final UpdateRepository<MODEL> updateRepository;

    public RepositoryAdapter(EntityManager em) {
        entityManagerThreadLocal.set(em);
        deleteRepository = new SimpleDeleteRepository<>(entityManagerThreadLocal.get());
        searchRepository = new SimpleSearchRepository<>(entityManagerThreadLocal.get());
        crudRepository = new SimpleCrudRepository<>(entityManagerThreadLocal.get());
        updateRepository = new SimpleUpdateRepository<>(entityManagerThreadLocal.get());
    }

    @Override
    public MODEL save(MODEL create) {
        return crudRepository.save(create);
    }

    @Override
    public MODEL get(Long id) {
        return crudRepository.get(id);
    }


    @Override
    public Long delete(Long id) {
        return crudRepository.delete(id);
    }

    @Override
    public MODEL update(MODEL update) {
        return crudRepository.update(update);
    }

    @Override
    public List<Long> delete(SpecificationDelete<MODEL> specification) {
        return deleteRepository.delete(specification);
    }

    @Override
    public Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable, boolean count) {
        return searchRepository.find(specification, pageable, count);
    }

    @Override
    public List<Long> update(SpecificationUpdate<MODEL> specification) {
        return updateRepository.update(specification);
    }

    @Override
    public void modelClass(Class<MODEL> modelClass) {
        deleteRepository.modelClass(modelClass);
        searchRepository.modelClass(modelClass);
        crudRepository.modelClass(modelClass);
        updateRepository.modelClass(modelClass);
    }


}
