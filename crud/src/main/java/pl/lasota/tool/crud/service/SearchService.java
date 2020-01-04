package pl.lasota.tool.crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.field.PaginationField;

import java.util.List;
import java.util.Optional;

public interface SearchService<READING> {

    Page<READING> find(List<Field<?>> source, Pageable pageable);

    default Page<READING> find(List<Field<?>> source) {
        Optional<PaginationField> first = source.stream().filter(field -> field instanceof PaginationField)
                .map(field -> (PaginationField) field).findFirst();

        PaginationField paginationField = first.orElse(new PaginationField(new pl.lasota.tool.crud.field.Pageable(0, 10)));

        pl.lasota.tool.crud.field.Pageable pageable = paginationField.getValue();
        return this.find(source, PageRequest.of(pageable.getPage(), pageable.getLimit()));
    }
}
