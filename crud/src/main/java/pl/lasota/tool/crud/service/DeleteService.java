package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.field.Field;

import java.util.List;

public interface DeleteService<MODEL> extends SpecificationProvider<MODEL> {

    List<Long> delete(List<Field<?>> source);
}
