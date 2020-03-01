package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.StringFields;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.base.Delete;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DeleteSecurity implements DeleteSecurityAction {

    private final Delete searchService;
    private final ProvidingPrivilege providingPrivilege;


    public DeleteSecurity(Delete searchService, ProvidingPrivilege providingPrivilege) {

        this.searchService = searchService;
        this.providingPrivilege = providingPrivilege;
    }

    @Override
    public List<Long> delete(List<Field<?>> source, String... privileges) {
        if (privileges == null) {
            throw new NullPointerException("privileges can not be null");
        }
        source.addAll(createFieldsSecured(privileges));
        return searchService.delete(source);
    }


    private List<Field<?>> createFieldsSecured(String... privileges) {
        String[] strings = Arrays.stream(providingPrivilege.update()).sorted().map(String::valueOf).toArray(String[]::new);
        StringFields securedPrivileges = StringFields.shouldBeOneOfThem(EntitySecurity.AUTHORIZATION_PRIVILEGED, Operator.EQUALS, privileges);
        StringFields securedPermission = StringFields.shouldBeOneOfThem(EntitySecurity.AUTHORIZATION_PERMISSION,  Operator.EQUALS, strings);

        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(securedPrivileges);
        fields.add(securedPermission);
        return fields;
    }
}
