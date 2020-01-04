package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.field.Field;

import java.util.List;

public interface UpdateService {

    List<Long> update(List<Field<?>> source);

}
