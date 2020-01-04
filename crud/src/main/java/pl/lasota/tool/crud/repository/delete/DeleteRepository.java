package pl.lasota.tool.crud.repository.delete;

import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.Repository;

import java.util.List;


public interface DeleteRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    List<Long> delete(SpecificationDelete<MODEL> specification);

}
