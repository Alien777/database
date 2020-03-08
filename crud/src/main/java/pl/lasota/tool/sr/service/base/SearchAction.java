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
public class SearchAction<READING, MODEL extends BasicEntity> implements Search<READING>,
        SpecificationProvider<SpecificationQuery<MODEL>, QueryCriteria> {

    private final SearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;


    public SearchAction(SearchRepository<MODEL> repository, Mapping<Page<MODEL>, Page<READING>> mapping, Class<MODEL> modelClass) {
        this.repository = repository;
        repository.modelClass(modelClass);
        this.mapping = mapping;

    }

    @Override
    public Page<READING> find(QueryCriteria queryCriteria) {
        Page<MODEL> models = repository.find(providerSpecification(queryCriteria),queryCriteria.getPageable());
        return mapping.mapper(models);
    }


    @Override
    public SpecificationQuery<MODEL> providerSpecification(QueryCriteria queryCriteria) {
        return new SearchCriteriaSpecification<>(queryCriteria, queryCriteria);
    }
}
