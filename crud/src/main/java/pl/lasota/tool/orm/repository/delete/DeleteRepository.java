package pl.lasota.tool.orm.repository.delete;

import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Repository;

import java.util.List;


public interface DeleteRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    List<Long> delete(SpecificationDelete<MODEL> specification);

}
