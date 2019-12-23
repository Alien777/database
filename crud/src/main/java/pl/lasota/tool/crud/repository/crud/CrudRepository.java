package pl.lasota.tool.crud.repository.crud;

import org.springframework.lang.Nullable;
import pl.lasota.tool.crud.repository.EntityBase;

public interface CrudRepository<MODEL extends EntityBase> {

    MODEL save(@Nullable MODEL create);

    MODEL get(@Nullable Long id);

    Long delete(@Nullable Long id);

    MODEL update(@Nullable MODEL create);

}
