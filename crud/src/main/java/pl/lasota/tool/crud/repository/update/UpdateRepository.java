package pl.lasota.tool.crud.repository.update;

import org.springframework.data.repository.NoRepositoryBean;
import pl.lasota.tool.crud.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface UpdateRepository<MODEL> extends Repository<MODEL> {

    List<MODEL> update(SpecificationUpdate<MODEL> specification);

}
