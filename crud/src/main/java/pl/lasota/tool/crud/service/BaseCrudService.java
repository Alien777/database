package pl.lasota.tool.crud.service;

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
    public READING save(CREATING create) {
        MODEL model = crudMapping.creatingToModel(create);
        MODEL save = repository.save(model);
        return crudMapping.modelToReading(save);
    }

    @Override
    public READING get(Long id) {
        Optional<MODEL> model = repository.findById(id);
        return model.map(crudMapping::modelToReading).orElse(null);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public READING update(UPDATING updating) {
        MODEL model = crudMapping.updatingToModel(updating);
        if (model.getId() == null) {
            return null;
        }
        MODEL updated = repository.save(model);
        return crudMapping.modelToReading(updated);
    }
}
