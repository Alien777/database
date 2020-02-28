package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.StringFields;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.UpdateService;
import pl.lasota.tool.sr.service.base.BaseUpdateService;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateSecurityService<MODEL extends EntitySecurity> {

    private final UpdateService searchService;
    private final ProvidingRules providingRules;


    public UpdateSecurityService(BaseUpdateService<MODEL> searchService, ProvidingRules providingRules) {
        this.searchService = searchService;
        this.providingRules = providingRules;
    }

    public List<Long> update(List<Field<?>> source, String... privileges) {
        source.addAll(createFieldsSecured(privileges));
        return searchService.update(source);
    }


    private List<Field<?>> createFieldsSecured(String... privileges) {
        String[] sortedPrivileges = Arrays.stream(privileges)
                .map(providingRules::createAllUpdatePrivilegeRud)
                .collect(Collectors.toList()).stream()
                .flatMap(Collection::stream).toArray(String[]::new);

        StringFields forCriteria = StringFields.shouldBeOneOfThem(EntitySecurity.FIELD_SECURED, Operator.EQUALS, sortedPrivileges);
        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(forCriteria);
        return fields;
    }
}
