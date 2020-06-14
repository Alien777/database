package pl.lasota.tool.sr.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.Repository;
import pl.lasota.tool.sr.repository.search.specification.SpecificationQuery;

public interface SearchRepository<MODEL extends BasicEntity> extends Repository<MODEL> {

    Page<MODEL> find(SpecificationQuery<MODEL> specification, Pageable pageable, boolean count);

}
