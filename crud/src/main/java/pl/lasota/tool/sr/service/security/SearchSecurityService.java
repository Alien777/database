package pl.lasota.tool.sr.service.security;

import org.springframework.data.domain.Page;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.StringFields;
import pl.lasota.tool.sr.service.SearchService;
import pl.lasota.tool.sr.service.base.BaseFullTextSearchService;
import pl.lasota.tool.sr.service.base.BaseSearchService;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SearchSecurityService<READING, MODEL extends EntitySecurity> {

    private final SearchService<READING> searchService;
    private final Operator operator;
    private final ProvidingRules providingRules;

    public SearchSecurityService(BaseSearchService<READING, MODEL> searchService, ProvidingRules providingRules) {
        this.searchService = searchService;
        this.providingRules = providingRules;
        operator = Operator.EQUALS;
    }

    public SearchSecurityService(BaseFullTextSearchService<READING, MODEL> searchService, ProvidingRules providingRules) {
        this.searchService = searchService;
        this.providingRules = providingRules;
        operator = Operator.KEYWORD;
    }

    public Page<READING> find(List<Field<?>> source, String... privileges) {
        source.addAll(createFieldsSecured(privileges));
        return searchService.find(source);
    }

    private List<Field<?>> createFieldsSecured(String... privileges) {
        String[] sortedPrivileges = Arrays.stream(privileges)
                .map(providingRules::createAllReadPrivilegeRud)
                .collect(Collectors.toList()).stream()
                .flatMap(Collection::stream)
                .toArray(String[]::new);

        StringFields securedField = StringFields.shouldBeOneOfThem(EntitySecurity.FIELD_SECURED, operator, sortedPrivileges);
        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(securedField);
        return fields;
    }
}
