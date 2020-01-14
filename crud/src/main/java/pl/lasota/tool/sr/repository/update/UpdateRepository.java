package pl.lasota.tool.sr.repository.update;

import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.Repository;
import pl.lasota.tool.sr.repository.update.specification.SpecificationUpdate;

import java.util.List;

public interface UpdateRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    List<Long> update(SpecificationUpdate<MODEL> specification);
}