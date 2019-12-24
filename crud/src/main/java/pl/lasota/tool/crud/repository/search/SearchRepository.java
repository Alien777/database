package pl.lasota.tool.crud.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.Repository;

public interface SearchRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable);

}
