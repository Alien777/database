package pl.lasota.tool.orm.service;

import pl.lasota.tool.orm.field.Field;

import java.util.List;

public interface UpdateService {

    List<Long> update(List<Field<?>> source);

}
