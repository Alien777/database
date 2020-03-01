package pl.lasota.tool.sr.service.base;

import pl.lasota.tool.sr.field.Field;

import java.util.List;

public interface Update {

    List<Long> update(List<Field<?>> source);

}
