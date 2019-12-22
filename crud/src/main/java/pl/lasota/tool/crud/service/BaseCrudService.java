package pl.lasota.tool.crud.service;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.repository.CrudRepository;
import pl.lasota.tool.crud.mapping.CrudMapping;
import pl.lasota.tool.crud.repository.EntityBase;

import java.util.Optional;

public class BaseCrudService<CREATING, READING, UPDATING, MODEL extends EntityBase>
        implements CrudService<CREATING, READING, UPDATING> {

    private final CrudRepository<MODEL> repository;

    private final CrudMapping<CREATING, READING, UPDATING, MODEL> crudMapping;

    public BaseCrudService(CrudRepository<MODEL> repository, CrudMapping<CREATING, READING, UPDATING, MODEL> crudMapping) {
        this.repository = repository;
        this.crudMapping = crudMapping;
    }

    @Override
    @Transactional
    public READING save(CREATING create) {
        MODEL model = crudMapping.creatingToModel(create);
        model.setId(null);
        MODEL save = repository.save(model);
        if (save == null) {
            return null;
        }
        return crudMapping.modelToReading(save);
    }

    @Override
    public READING get(Long id) {
        MODEL model = repository.get(id);
        if (model == null) {
            return null;
        }
        return crudMapping.modelToReading(model);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    @Transactional
    public READING update(UPDATING updating) {
        MODEL model = crudMapping.updatingToModel(updating);
        if (model.getId() == null) {
            return null;
        }
        MODEL updated = repository.save(model);
        if (updated == null) {
            return null;
        }
        return crudMapping.modelToReading(updated);
    }
}
