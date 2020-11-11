package pl.lasota.database.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.Repository;
import pl.lasota.database.repository.search.specification.SpecificationQuery;

public interface SearchRepository<MODEL extends BasicEntity> extends Repository<MODEL> {

    Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable, boolean count);

}
