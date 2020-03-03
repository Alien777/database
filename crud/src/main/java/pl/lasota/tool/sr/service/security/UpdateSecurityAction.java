package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.field.definition.Field;

import java.util.List;

public interface UpdateSecurityAction {

    List<Long> update(List<Field<?>> source, String... privileges);
}
