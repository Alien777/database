package pl.lasota.tool.crud.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.fulltextrepository.FullTextSearchRepository;
import pl.lasota.tool.crud.fulltextrepository.SearchSpecificationFullText;
import pl.lasota.tool.crud.fulltextrepository.distributed.DistributeFullTextFactory;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.mapping.FieldMapping;
import pl.lasota.tool.crud.service.SearchService;

import java.util.List;

public class BaseFullTextSearchService<READING, MODEL extends EntityBase> implements SearchService<READING, Object> {

    private final FullTextSearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;

    public BaseFullTextSearchService(FullTextSearchRepository<MODEL> repository, Mapping<Page<MODEL>, Page<READING>> mapping) {
        this.repository = repository;
        this.mapping = mapping;
    }


    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        return null;
    }

    @Override
    public Page<READING> find(List<Field<?>> source) {
        return null;
    }


    @Override
    public Specification providerSpecification(List<Field<?>> fields) {
        FieldMapping<MODEL> map = new FieldMapping<>();
        new SearchSpecificationFullText(new DistributeFullTextFactory());
       return  null;
    }


}
