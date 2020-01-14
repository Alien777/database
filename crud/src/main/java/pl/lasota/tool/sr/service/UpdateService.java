package pl.lasota.tool.sr.service;

import pl.lasota.tool.sr.field.Field;

import java.util.List;

public interface UpdateService {

    List<Long> update(List<Field<?>> source);

}
