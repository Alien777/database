package pl.lasota.tool.crud.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.repository.mapping.FieldMapping;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;
import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;
import pl.lasota.tool.crud.service.SearchService;
import pl.lasota.tool.crud.service.SpecificationProvider;

import java.util.List;

@Transactional(readOnly = true)
public class BaseSearchService<READING, MODEL extends EntityBase> implements SearchService<READING>,
        SpecificationProvider<Specification<MODEL>> {

    private final SearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;
    private final FieldMapping<MODEL> map;

    public BaseSearchService(SearchRepository<MODEL> repository, Mapping<Page<MODEL>, Page<READING>> mapping, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        this.mapping = mapping;
        map = new FieldMapping<>(modelClass);
    }

    @Override

    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        Page<MODEL> models = repository.find(providerSpecification(source), pageable);
        return mapping.mapper(models);
    }

    @Override
    public SpecificationQuery<MODEL> providerSpecification(List<Field<?>> fields) {

        return new SearchCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map, map, map));
    }
}
