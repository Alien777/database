package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.field.Field;

import java.util.List;

public interface UpdateSecurityAction {

    List<Long> update(List<Field<?>> source, String... privileges);
    List<Long> update(List<Field<?>> source, String user,  String... privileges);
    List<Long> update(List<Field<?>> source, String user, String group, String... privileges);
}
