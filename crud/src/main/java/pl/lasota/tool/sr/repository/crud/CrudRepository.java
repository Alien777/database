package pl.lasota.tool.sr.repository.crud;

import org.springframework.lang.Nullable;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.Repository;
public interface CrudRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    MODEL save(@Nullable MODEL create);

    MODEL get(@Nullable Long id);

    Long delete(@Nullable Long id);

    MODEL update(@Nullable MODEL create);

}
