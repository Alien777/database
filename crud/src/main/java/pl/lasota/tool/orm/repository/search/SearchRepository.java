package pl.lasota.tool.orm.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Repository;

public interface SearchRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable);

}
