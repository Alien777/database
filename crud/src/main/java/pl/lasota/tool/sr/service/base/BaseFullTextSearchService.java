package pl.lasota.tool.sr.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.mapping.Mapping;
import pl.lasota.tool.sr.repository.DistributeTokenFactory;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.TokenFieldMapping;
import pl.lasota.tool.sr.repository.tokensearch.TokenSearchRepository;
import pl.lasota.tool.sr.repository.tokensearch.specification.SearchSpecificationSearch;
import pl.lasota.tool.sr.repository.tokensearch.specification.SpecificationSearchToken;
import pl.lasota.tool.sr.service.SearchService;
import pl.lasota.tool.sr.service.SpecificationProvider;

import java.util.List;

public class BaseFullTextSearchService<READING, MODEL extends EntityBase> implements SearchService<READING>, SpecificationProvider<SpecificationSearchToken> {

    private final TokenSearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;

    private final TokenFieldMapping tokenFieldMapping = new TokenFieldMapping();


    public BaseFullTextSearchService(TokenSearchRepository<MODEL> repository, Mapping<Page<MODEL>, Page<READING>> mapping) {
        this.repository = repository;
        this.mapping = mapping;
    }

    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        Page<MODEL> models = repository.find(providerSpecification(source), pageable);
        return mapping.mapper(models);
    }


    @Override
    public SpecificationSearchToken providerSpecification(List<Field<?>> fields) {
        return new SearchSpecificationSearch(new DistributeTokenFactory(filter(fields), tokenFieldMapping));
    }
}
