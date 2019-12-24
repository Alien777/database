package pl.lasota.tool.crud.repository.crud;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.Nullable;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.Repository;

@NoRepositoryBean
public interface CrudRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    MODEL save(@Nullable MODEL create);

    MODEL get(@Nullable Long id);

    Long delete(@Nullable Long id);

    MODEL update(@Nullable MODEL create);

}
