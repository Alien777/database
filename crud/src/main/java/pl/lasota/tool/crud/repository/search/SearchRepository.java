package pl.lasota.tool.crud.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;
import pl.lasota.tool.crud.repository.EntityBase;

@NoRepositoryBean
public interface SearchRepository<MODEL extends EntityBase> {

    Page<MODEL> find(@Nullable SpecificationQuery<MODEL> specification, Pageable pageable);

}
