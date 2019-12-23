package pl.lasota.tool.crud.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.repository.EntityBase;
import pl.lasota.tool.crud.repository.search.SearchRepository;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;
import pl.lasota.tool.crud.serach.field.Field;
import pl.lasota.tool.crud.serach.field.PaginationField;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
public abstract class BaseSearchService<READING, MODEL extends EntityBase> implements SearchService<READING> {

    private final SearchRepository<MODEL> repository;
    private final Mapping<Page<MODEL>, Page<READING>> mapping;

    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        Page<MODEL> models = repository.find(providerSpecificationQuery(source), pageable);
        return mapping.mapper(models);

    }

    @Override
    public Page<READING> find(List<Field<?>> source) {
        Optional<PaginationField> first = source.stream().filter(field -> field instanceof PaginationField)
                .map(field -> (PaginationField) field).findFirst();

        PaginationField paginationField = first.orElse(new PaginationField(new pl.lasota.tool.crud.serach.field.Page(0, 10)));

        pl.lasota.tool.crud.serach.field.Page page = paginationField.getValue();
        return this.find(source, PageRequest.of(page.getPage(), page.getLimit()));
    }

    protected abstract SpecificationQuery<MODEL> providerSpecificationQuery(List<Field<?>> fields);
}
