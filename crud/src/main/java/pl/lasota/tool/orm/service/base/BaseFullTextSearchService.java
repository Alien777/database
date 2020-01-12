package pl.lasota.tool.orm.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.field.Field;
import pl.lasota.tool.orm.fulltextrepository.FullTextSearchRepository;
import pl.lasota.tool.orm.fulltextrepository.SearchSpecificationFullText;
import pl.lasota.tool.orm.fulltextrepository.SpecificationFullText;
import pl.lasota.tool.orm.fulltextrepository.distributed.DistributeFullTextFactory;
import pl.lasota.tool.orm.fulltextrepository.mapping.MustMapping;
import pl.lasota.tool.orm.fulltextrepository.mapping.NotMustMapping;
import pl.lasota.tool.orm.fulltextrepository.mapping.ShouldMapping;
import pl.lasota.tool.orm.fulltextrepository.mapping.SortMapping;
import pl.lasota.tool.orm.mapping.Mapping;
import pl.lasota.tool.orm.service.SearchService;
import pl.lasota.tool.orm.service.SpecificationProvider;

import java.util.List;

public class BaseFullTextSearchService<READING, MODEL extends EntityBase> implements SearchService<READING>, SpecificationProvider<SpecificationFullText> {

    private final FullTextSearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;
    private final MustMapping mustMapping = new MustMapping();
    private final NotMustMapping notMustMapping = new NotMustMapping();
    private final ShouldMapping shouldMapping = new ShouldMapping();
    private final SortMapping sortMapping = new SortMapping();

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
        return new SearchSpecificationFullText(new DistributeFullTextFactory(filter(fields), mustMapping, notMustMapping,
                shouldMapping, sortMapping));
    }
}
