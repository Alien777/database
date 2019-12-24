package pl.lasota.tool.crud.repository.delete;

import org.springframework.data.repository.NoRepositoryBean;
import pl.lasota.tool.crud.repository.Repository;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;

import java.util.List;

@NoRepositoryBean
public interface DeleteRepository<MODEL> extends Repository<MODEL> {

    List<Long> delete(SpecificationDelete<MODEL> specification);

}
