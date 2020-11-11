package pl.lasota.database.repository.crud;

import org.springframework.lang.Nullable;
import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.Repository;
public interface CrudRepository<MODEL extends BasicEntity> extends Repository<MODEL> {

    MODEL save(@Nullable MODEL create);

    MODEL get(@Nullable Long id);

    Long delete(@Nullable Long id);

    MODEL update(@Nullable MODEL create);

}
