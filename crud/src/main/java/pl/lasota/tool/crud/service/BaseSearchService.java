package pl.lasota.tool.crud.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;
import pl.lasota.tool.crud.repository.DistributeFieldFactory;
import pl.lasota.tool.crud.repository.FieldMapperFields;
import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.field.PaginationField;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public class BaseSearchService<READING, MODEL extends EntityBase> implements SearchService<READING, MODEL> {

    private final SearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;
    private final Class<MODEL> modelClass;

    @Override
    @Transactional(readOnly = true)
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        Page<MODEL> models = repository.find(providerSpecification(source), pageable);
        return mapping.mapper(models);

    }

    @Override
    @Transactional(readOnly = true)
    public Page<READING> find(List<Field<?>> source) {
        Optional<PaginationField> first = source.stream().filter(field -> field instanceof PaginationField)
                .map(field -> (PaginationField) field).findFirst();

        PaginationField paginationField = first.orElse(new PaginationField(new pl.lasota.tool.crud.field.Page(0, 10)));

        pl.lasota.tool.crud.field.Page page = paginationField.getValue();
        return this.find(source, PageRequest.of(page.getPage(), page.getLimit()));
    }


    @Override
    public SpecificationQuery<MODEL> providerSpecification(List<Field<?>> fields) {
        return new SearchCriteriaSpecification<>(new DistributeFieldFactory<>(filter(fields), new FieldMapperFields<>(), modelClass));
    }
}
