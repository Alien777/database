package pl.lasota.tool.crud.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.field.Field;

import java.util.List;

public interface DeleteService<MODEL> extends SpecificationProvider<MODEL> {

    List<Long> delete(List<Field<?>> source);
}
