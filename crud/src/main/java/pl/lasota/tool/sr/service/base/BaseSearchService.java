package pl.lasota.tool.sr.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.repository.Specification;
import pl.lasota.tool.sr.field.DistributeForField;
import pl.lasota.tool.sr.repository.CriteriaFieldMapping;
import pl.lasota.tool.sr.repository.search.SearchRepository;
import pl.lasota.tool.sr.repository.search.specification.SpecificationQuery;
import pl.lasota.tool.sr.repository.search.specification.SearchCriteriaSpecification;
import pl.lasota.tool.sr.service.SearchService;
import pl.lasota.tool.sr.service.SpecificationProvider;

import java.util.List;

@Transactional(readOnly = true)
public class BaseSearchService<READING, MODEL extends EntityBase> implements SearchService<READING>,
        SpecificationProvider<Specification<MODEL>> {

    private final SearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;
    private final CriteriaFieldMapping<MODEL> map;

    public BaseSearchService(SearchRepository<MODEL> repository, Mapping<Page<MODEL>, Page<READING>> mapping, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        this.mapping = mapping;
        map = new CriteriaFieldMapping<>(modelClass);
    }

    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        Page<MODEL> models = repository.find(providerSpecification(source), pageable);
        return mapping.mapper(models);
    }

    @Override
    public SpecificationQuery<MODEL> providerSpecification(List<Field<?>> fields) {
        return new SearchCriteriaSpecification<>(new DistributeForField(filter(fields)), map);
    }
}
