package pl.lasota.tool.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CrudRepository<MODEL> extends JpaRepository<MODEL, Long> {
}
