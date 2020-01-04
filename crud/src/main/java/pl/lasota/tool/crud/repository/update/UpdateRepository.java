package pl.lasota.tool.crud.repository.update;

import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.Repository;

import java.util.List;

public interface UpdateRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    List<Long> update(SpecificationUpdate<MODEL> specification);
}
