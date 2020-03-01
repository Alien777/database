package pl.lasota.tool.sr.service.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.PaginationField;

import java.util.List;
import java.util.Optional;

public interface SearchSecurityAction<READING> {

    Page<READING> find(List<Field<?>> source, Pageable pageable, String... privilege);

    Page<READING> find(List<Field<?>> source, Pageable pageable, String user, String group, String... privilege);

    Page<READING> find(List<Field<?>> source, Pageable pageable, String user, String... privilege);

    default Page<READING> find(List<Field<?>> source, String... privilege) {
        Optional<PaginationField> first = source.stream().filter(field -> field instanceof PaginationField)
                .map(field -> (PaginationField) field).findFirst();

        PaginationField paginationField = first.orElse(new PaginationField(new pl.lasota.tool.sr.field.Pageable(0, 10)));

        pl.lasota.tool.sr.field.Pageable pageable = paginationField.getValue();
        return this.find(source, PageRequest.of(pageable.getPage(), pageable.getLimit()), privilege);
    }
}
