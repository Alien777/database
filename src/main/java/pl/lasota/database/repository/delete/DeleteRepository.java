package pl.lasota.database.repository.delete;

import pl.lasota.database.repository.delete.specification.SpecificationDelete;
import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.Repository;

import java.util.List;


public interface DeleteRepository<MODEL extends BasicEntity> extends Repository<MODEL> {

    List<Long> delete(SpecificationDelete<MODEL> specification);

}
