package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.field.definition.Field;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.definition.MultipleValuesField;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.base.Update;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class UpdateSecurity implements UpdateSecurityAction {

    private final Update searchService;
    private final ProvidingPrivilege providingPrivilege;


    public UpdateSecurity(Update searchService, ProvidingPrivilege providingPrivilege) {
        this.searchService = searchService;
        this.providingPrivilege = providingPrivilege;
    }

    @Override
    public List<Long> update(List<Field<?>> source, String... privileges) {
        if (privileges == null) {
            throw new NullPointerException("privileges can not be null");
        }
        source.addAll(createFieldsSecured(privileges));
        return searchService.update(source);
    }

    private List<Field<?>> createFieldsSecured(String... privileges) {

        String[] strings = Arrays.stream(providingPrivilege.update()).sorted().map(String::valueOf).toArray(String[]::new);
        MultipleValuesField securedPrivileges = MultipleValuesField.shouldBeOneOfThem(EntitySecurity.AUTHORIZATION_PRIVILEGED, Operator.EQUALS, privileges);
        MultipleValuesField securedPermission = MultipleValuesField.shouldBeOneOfThem(EntitySecurity.AUTHORIZATION_PERMISSION, Operator.EQUALS, strings);

        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(securedPrivileges);
        fields.add(securedPermission);
        return fields;
    }
}
