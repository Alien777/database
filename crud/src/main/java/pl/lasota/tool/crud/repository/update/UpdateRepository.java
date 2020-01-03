package pl.lasota.tool.crud.repository.update;

import pl.lasota.tool.crud.repository.Repository;

import java.util.List;

public interface UpdateRepository<MODEL> extends Repository<MODEL> {

    List<Long> update(SpecificationUpdate<MODEL> specification);

}
