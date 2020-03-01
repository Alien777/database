package pl.lasota.tool.sr.service.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.StringFields;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.base.Search;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SearchSecurity<READING> implements SearchSecurityAction<READING> {

    private final Search<READING> search;
    private final Operator operator;
    private final ProvidingPrivilege providingPrivilege;

    public SearchSecurity(Search<READING> search, ProvidingPrivilege providingPrivilege) {

        this.search = search;
        this.providingPrivilege = providingPrivilege;
        operator = Operator.EQUALS;
    }

    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable, String... privileges) {
        if (privileges == null) {
            throw new NullPointerException("privileges can not be null");
        }
        source.addAll(createFieldsSecured(privileges));
        return search.find(source, pageable);
    }

    private List<Field<?>> createFieldsSecured(String... privileges) {

        String[] strings = Arrays.stream(providingPrivilege.read()).sorted().map(String::valueOf).toArray(String[]::new);
        StringFields securedPrivileges = StringFields.shouldBeOneOfThem(EntitySecurity.AUTHORIZATION_PRIVILEGED, operator, privileges);
        StringFields securedPermission = StringFields.shouldBeOneOfThem(EntitySecurity.AUTHORIZATION_PERMISSION, operator, strings);

        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(securedPrivileges);
        fields.add(securedPermission);
        return fields;
    }
}
