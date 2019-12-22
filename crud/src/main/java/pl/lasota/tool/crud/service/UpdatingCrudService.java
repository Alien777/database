package pl.lasota.tool.crud.service;

import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.mapping.CrudMapping;
import pl.lasota.tool.crud.repository.*;

import java.util.List;

public class UpdatingCrudService<CREATING, READING, UPDATING, MODEL extends EntityBase>
        extends BaseCrudService<CREATING, READING, UPDATING, MODEL> {

    private UpdateRepository<MODEL> repository;

    public UpdatingCrudService(UpdateFieldRepository<MODEL> repository, CrudMapping<CREATING, READING, UPDATING, MODEL> crudMapping) {
        super(repository, crudMapping);
        this.repository = repository;
    }


}
