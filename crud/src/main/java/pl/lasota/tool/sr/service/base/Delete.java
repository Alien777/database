package pl.lasota.tool.sr.service.base;

import pl.lasota.tool.sr.field.definition.Field;

import java.util.List;

public interface Delete {

    List<Long> delete(List<Field<?>> source);
}
