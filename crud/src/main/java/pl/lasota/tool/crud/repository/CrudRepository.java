package pl.lasota.tool.crud.repository;

import org.springframework.lang.Nullable;

public interface CrudRepository<MODEL> {

    MODEL save(@Nullable MODEL create);

    MODEL get(@Nullable Long id);

    void delete(@Nullable Long id);

}
