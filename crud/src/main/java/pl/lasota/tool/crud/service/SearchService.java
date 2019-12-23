package pl.lasota.tool.crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.serach.field.Field;

import java.util.List;

public interface SearchService<READING, MODEL> extends SpecificationProvider<MODEL> {

    Page<READING> find(List<Field<?>> source, Pageable pageable);

    Page<READING> find(List<Field<?>> source);
}
