package pl.lasota.tool.sr.service;

import pl.lasota.tool.sr.field.Field;

import java.util.List;

public interface DeleteService {

    List<Long> delete(List<Field<?>> source);
}
