package pl.lasota.tool.crud.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.fulltextrepository.FullTextSearchRepository;
import pl.lasota.tool.crud.fulltextrepository.SearchSpecificationFullText;
import pl.lasota.tool.crud.fulltextrepository.SpecificationFullText;
import pl.lasota.tool.crud.fulltextrepository.distributed.DistributeFullTextFactory;
import pl.lasota.tool.crud.fulltextrepository.mapping.MustMapping;
import pl.lasota.tool.crud.fulltextrepository.mapping.NotMustMapping;
import pl.lasota.tool.crud.fulltextrepository.mapping.ShouldMapping;
import pl.lasota.tool.crud.fulltextrepository.mapping.SortMapping;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.service.SearchService;
import pl.lasota.tool.crud.service.SpecificationProvider;

import java.util.List;

public class BaseFullTextSearchService<READING, MODEL extends EntityBase> implements SearchService<READING>, SpecificationProvider<SpecificationFullText> {

    private final FullTextSearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;

    public BaseFullTextSearchService(FullTextSearchRepository<MODEL> repository, Mapping<Page<MODEL>, Page<READING>> mapping) {
        this.repository = repository;
        this.mapping = mapping;
    }


    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        Page<MODEL> models = repository.find(providerSpecification(source), pageable);
        return mapping.mapper(models);
    }


    @Override
    public SpecificationFullText providerSpecification(List<Field<?>> fields) {
        MustMapping mustMapping = new MustMapping();
        NotMustMapping notMustMapping = new NotMustMapping();
        ShouldMapping shouldMapping = new ShouldMapping();
        SortMapping sortMapping = new SortMapping();

        return new SearchSpecificationFullText(new DistributeFullTextFactory(filter(fields), mustMapping, notMustMapping,
                shouldMapping, sortMapping));
    }
}
