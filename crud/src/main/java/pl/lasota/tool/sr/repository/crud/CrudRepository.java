package pl.lasota.tool.sr.repository.crud;

import org.springframework.lang.Nullable;
import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.Repository;
public interface CrudRepository<MODEL extends BasicEntity> extends Repository<MODEL> {

    MODEL save(@Nullable MODEL create);

    MODEL get(@Nullable Long id);

    Long delete(@Nullable Long id);

    MODEL update(@Nullable MODEL create);

}
