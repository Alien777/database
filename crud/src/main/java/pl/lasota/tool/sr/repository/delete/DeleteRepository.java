package pl.lasota.tool.sr.repository.delete;

import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.Repository;
import pl.lasota.tool.sr.repository.delete.specification.SpecificationDelete;

import java.util.List;


public interface DeleteRepository<MODEL extends BasicEntity> extends Repository<MODEL> {

    List<Long> delete(SpecificationDelete<MODEL> specification);

}
