package pl.lasota.database.service.base;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.crud.CrudRepository;
import pl.lasota.database.mapping.DozerSameObject;

@Transactional(readOnly = true)
public class CrudNoMappingAction<MODEL extends BasicEntity> implements Crud<MODEL, MODEL, MODEL> {

    private final CrudRepository<MODEL> repository;

    private final DozerSameObject<MODEL> modelToModel;

    public CrudNoMappingAction(CrudRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        modelToModel = new DozerSameObject<>(modelClass);
    }

    @Override
    @Transactional
    public MODEL save(MODEL create) {
        create.setId(null);
        return repository.save(create);
    }

    @Override
    public MODEL get(Long id) {
        return repository.get(id);
    }

    @Override
    @Transactional
    public Long delete(Long id) {
        return repository.delete(id);
    }

    @Override
    @Transactional
    public MODEL update(Long id, MODEL updating) {
        if (updating == null
                || id == null
                || id <= 0) {
            return null;
        }
        MODEL inDatabase = repository.get(id);
        modelToModel.mapper(updating, inDatabase);
        return inDatabase;
    }
}
