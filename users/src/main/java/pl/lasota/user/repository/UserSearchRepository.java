package pl.lasota.user.repository;

import org.springframework.stereotype.Service;
import pl.lasota.tool.crud.mapping.DozerPageMapping;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.criteria.DistributeFieldFactory;
import pl.lasota.tool.crud.repository.search.criteria.FieldMapperFields;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.criteria.UpdateCriteriaSpecification;
import pl.lasota.tool.crud.serach.field.Field;
import pl.lasota.tool.crud.service.BaseSearchService;
import pl.lasota.user.User;

import java.util.List;

@Service
public class SearchService extends BaseSearchService<User, User> {

    public SearchService(SearchRepository<User> repository) {
        super(repository, new DozerPageMapping<>(User.class));
    }

    @Override
    protected SpecificationUpdate<User> providerSpecification(List<Field<?>> fields) {
        return new UpdateCriteriaSpecification<>(new DistributeFieldFactory<>(fields, new FieldMapperFields<>()));
    }
}
