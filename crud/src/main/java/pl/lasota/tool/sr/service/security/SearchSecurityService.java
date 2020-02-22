package pl.lasota.tool.sr.service.security;

import org.springframework.data.domain.Page;
import pl.lasota.tool.sr.security.Access;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.StringFields;
import pl.lasota.tool.sr.security.AccessContext;
import pl.lasota.tool.sr.security.Context;
import pl.lasota.tool.sr.service.SearchService;
import pl.lasota.tool.sr.service.base.BaseFullTextSearchService;
import pl.lasota.tool.sr.service.base.BaseSearchService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchSecurityService<READING, MODEL extends EntitySecurity> {

    private static final String FIELD_SECURED = "accesses.value";
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

    public Page<READING> find(List<Field<?>> source, Context context) {
        source.addAll(createFieldsSecured(context));
        return searchService.find(source);
    }

    private List<Field<?>> createFieldsSecured(Context context) {
        String[] strings = context.getSecured().stream().map(s -> {
            Stream<String> stringStream = providingRules.forCanRead().stream()
                    .map(r -> s.getName() + Access.SEPARATOR + r);
            return stringStream.collect(Collectors.toList());
        }).collect(Collectors.toList()).stream().flatMap(Collection::stream).toArray(String[]::new);

        StringFields securedField = StringFields.shouldBeOneOfThem(FIELD_SECURED, operator, strings);
        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(securedField);
        return fields;
    }
}
