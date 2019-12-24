package pl.lasota.user;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerMapper;
import pl.lasota.tool.crud.mapping.DozerPageMapping;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.crud.CrudRepository;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.criteria.DistributeFieldFactory;
import pl.lasota.tool.crud.repository.search.criteria.FieldMapperFields;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.criteria.UpdateCriteriaSpecification;
import pl.lasota.tool.crud.serach.field.Field;
import pl.lasota.tool.crud.service.BaseCrudService;
import pl.lasota.tool.crud.service.BaseSearchService;

import java.util.List;

@Service
public class CrudService extends BaseCrudService<User, User, User, User> {

    public CrudService(CrudRepository<User> repository) {
        super(repository, new DozerMapper<>(User.class), new DozerMapper<>(User.class), new DozerMapper<>(User.class));
    }
}
