package pl.lasota.tool.crud.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface SearchRepository<MODEL extends EntityBase, ID extends Number>
        extends Repository<MODEL, ID>, JpaSpecificationExecutor<MODEL> {
}
