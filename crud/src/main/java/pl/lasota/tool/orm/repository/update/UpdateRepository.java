package pl.lasota.tool.orm.repository.update;

import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Repository;

import java.util.List;

public interface UpdateRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    List<Long> update(SpecificationUpdate<MODEL> specification);
}
