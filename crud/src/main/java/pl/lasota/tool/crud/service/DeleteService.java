package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.field.Field;

import java.util.List;

public interface DeleteService {

    List<Long> delete(List<Field<?>> source);
}
