package pl.lasota.tool.crud.repository.update;

import java.util.List;

public interface UpdateRepository<MODEL> {

    List<MODEL> update(SpecificationUpdate<MODEL> specification);

}
