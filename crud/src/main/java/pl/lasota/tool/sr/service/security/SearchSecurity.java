package pl.lasota.tool.sr.service.security;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.field.definition.Field;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.definition.MultipleValuesField;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.base.Search;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SearchSecurity<READING> implements SearchSecurityAction<READING> {

    private final Search<READING> search;
    private final Operator operator;
    private final ProvidingPrivilege providingPrivilege;
    private ProvidingContext providingContext;

    public SearchSecurity(Search<READING> search, ProvidingPrivilege providingPrivilege, ProvidingContext providingContext) {

        this.search = search;
        this.providingPrivilege = providingPrivilege;
        this.providingContext = providingContext;
        operator = Operator.EQUALS;
    }

    @Override
    public Page<READING> find(List<Field<?>> source, Pageable pageable) {
        source.addAll(createFieldsSecured());
        return search.find(source, pageable);
    }

    private List<Field<?>> createFieldsSecured() {
        Context supply = providingContext.supply();

        String[] privileges = supply.getPrivilege().toArray(String[]::new);
        MultipleValuesField securedPrivileges = MultipleValuesField.shouldBeOneOfThem(EntitySecurity.AUTHORIZATION_PRIVILEGED, operator, privileges);
        String[] strings = Arrays.stream(providingPrivilege.read()).sorted().map(String::valueOf).toArray(String[]::new);
        MultipleValuesField securedPermission = MultipleValuesField.shouldBeOneOfThem(EntitySecurity.AUTHORIZATION_PERMISSION, operator, strings);

        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(securedPrivileges);
        fields.add(securedPermission);
        return fields;
    }
}
