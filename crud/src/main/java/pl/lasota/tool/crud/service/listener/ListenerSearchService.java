package pl.lasota.tool.crud.service.listener;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.field.PaginationField;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.mapping.FieldMapping;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;
import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;
import pl.lasota.tool.crud.service.SearchService;

import java.util.List;
import java.util.Optional;

public class ListenerSearchService<READING, MODEL extends EntityBase> implements SearchService<READING, MODEL> {

    private final SearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;

    public ListenerSearchService(SearchRepository<MODEL> repository, Mapping<Page<MODEL>, Page<READING>> mapping) {
        this.repository = repository;
        this.mapping = mapping;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        Page<MODEL> models = repository.find(providerSpecification(source), pageable);
        return mapping.mapper(models);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<READING> find(List<Field<?>> source) {
        Optional<PaginationField> first = source.stream()
                .filter(field -> field instanceof PaginationField)
                .map(field -> (PaginationField) field).findFirst();

        PaginationField paginationField = first.orElse(new PaginationField(new pl.lasota.tool.crud.field.Pageable(0, 10)));

        pl.lasota.tool.crud.field.Pageable pageable = paginationField.getValue();
        return this.find(source, PageRequest.of(pageable.getPage(), pageable.getLimit()));
    }


    @Override
    public SpecificationQuery<MODEL> providerSpecification(List<Field<?>> fields) {
        FieldMapping<MODEL> map = new FieldMapping<>();
        return new SearchCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map, map, map));
    }
}
