package pl.lasota.tool.crud.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.lang.Nullable;

@NoRepositoryBean
public interface SearchRepository<MODEL extends EntityBase> extends Repository<MODEL, Long>, JpaSpecificationExecutor<MODEL> {

    @Override
    Page<MODEL> findAll(@Nullable Specification<MODEL> spec, Pageable pageable);

    @Override
    long count(@Nullable Specification<MODEL> spec);
}
