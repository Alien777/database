package pl.lasota.tool.sr.service.base;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.query.QueryCriteria;
import pl.lasota.tool.sr.repository.search.SearchRepository;
import pl.lasota.tool.sr.repository.search.specification.SearchCriteriaSpecification;
import pl.lasota.tool.sr.repository.search.specification.SpecificationQuery;

@Transactional(readOnly = true)
public class SearchNoMappingAction<MODEL extends BasicEntity> implements Search<MODEL>,
        SpecificationProvider<SpecificationQuery<MODEL>, QueryCriteria> {

    private final SearchRepository<MODEL> repository;

    public SearchNoMappingAction(SearchRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
    }

    @Override
    public Page<MODEL> find(QueryCriteria queryCriteria) {
        return repository.find(providerSpecification(queryCriteria), queryCriteria.getPageable(), false);
    }

    @Override
    public Page<MODEL> findCount(QueryCriteria queryCriteria) {
        return repository.find(providerSpecification(queryCriteria), queryCriteria.getPageable(), true);
    }


    @Override
    public SpecificationQuery<MODEL> providerSpecification(QueryCriteria queryCriteria) {
        return new SearchCriteriaSpecification<>(queryCriteria, queryCriteria);
    }
}
