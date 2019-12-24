package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.field.Field;

import java.util.List;

public interface UpdateService<READING, MODEL> extends SpecificationProvider<MODEL> {

    List<READING> update(List<Field<?>> source);

}
