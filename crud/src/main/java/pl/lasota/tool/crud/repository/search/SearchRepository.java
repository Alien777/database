package pl.lasota.tool.crud.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.Repository;

@NoRepositoryBean
public interface SearchRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    Page<MODEL> find(@Nullable SpecificationQuery<MODEL> specification, Pageable pageable);

}
