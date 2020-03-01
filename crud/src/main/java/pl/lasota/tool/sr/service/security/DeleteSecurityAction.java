package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.field.Field;

import java.util.List;

public interface DeleteSecurityAction {

    List<Long> delete(List<Field<?>> source, String... privileges);
}
