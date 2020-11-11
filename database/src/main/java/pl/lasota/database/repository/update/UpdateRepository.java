package pl.lasota.database.repository.update;

import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.Repository;
import pl.lasota.database.repository.update.specification.SpecificationUpdate;

import java.util.List;

public interface UpdateRepository<MODEL extends BasicEntity> extends Repository<MODEL> {

    List<Long> update(SpecificationUpdate<MODEL> specification);
}
