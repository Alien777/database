package pl.lasota.tool.crud;

import pl.lasota.tool.crud.repository.CrudRepository;
import pl.lasota.tool.crud.service.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.mapping.CrudMapping;
import pl.lasota.tool.crud.repository.EntityBase;

import java.util.Optional;

public class MapperCrudService<CREATE, READ, UPDATING, MODEL extends EntityBase, ID extends Number>
        implements CrudService<CREATE, READ, UPDATING, ID> {

    private final CrudRepository<MODEL, ID> repository;

    private final CrudMapping<CREATE, READ, UPDATING, MODEL> crudMapping;

    public MapperCrudService(CrudRepository<MODEL, ID> repository, CrudMapping<CREATE, READ, UPDATING, MODEL> crudMapping) {
        this.repository = repository;
        this.crudMapping = crudMapping;
    }

    @Override
    public READ save(CREATE create) {
        MODEL model = crudMapping.createToModel(create);
        MODEL save = repository.save(model);
        return crudMapping.modelToRead(save);
    }

    @Override
    public READ get(ID id) {
        Optional<MODEL> model = repository.findById(id);
        return model.map(crudMapping::modelToRead).orElse(null);
    }

    @Override
    public Page<READ> getAll(Pageable pageable) {
        Page<MODEL> page = repository.findAll(pageable);
        return crudMapping.modelsToPage(page);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public READ update(UPDATING updating) {
        MODEL model = crudMapping.updatingToModel(updating);
        if (model.getId() == null) {
            return null;
        }
        MODEL updated = repository.save(model);
        return crudMapping.modelToRead(updated);
    }
}
