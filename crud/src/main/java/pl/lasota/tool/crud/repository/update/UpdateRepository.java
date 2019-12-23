package pl.lasota.tool.crud.repository.update;

import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface UpdateRepository<MODEL> {

    List<MODEL> update(SpecificationUpdate<MODEL> specification);

}
