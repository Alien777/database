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

public class SearchSecurityDelegator<READING, MODEL extends EntitySecurity> {

    private static final String FIELD_SECURED = "accesses.value";
    private final SearchService<READING> searchService;

    private final List<Integer> canRead = new LinkedList<>() {{
        add(4);
        add(5);
        add(6);
        add(7);
    }};

    public SearchSecurityDelegator(BaseSearchService<READING, MODEL> searchService) {
        this.searchService = searchService;
    }

    public SearchSecurityDelegator(BaseFullTextSearchService<READING, MODEL> searchService) {
        this.searchService = searchService;
    }

    public Page<READING> find(List<Field<?>> source, Context context) {
        source.addAll(createFieldsSecured(context));
        return searchService.find(source);
    }

    private List<Field<?>> createFieldsSecured(Context context) {
        Set<AccessContext> secureds = context.getSecured();

        String[] strings = secureds.stream().map(s -> {
            Stream<String> stringStream = canRead.stream().map(r -> s.getName() + Access.SEPARATOR + r);
            return stringStream.collect(Collectors.toList());
        }).collect(Collectors.toList()).stream().flatMap(Collection::stream).toArray(String[]::new);

        StringFields forCriteria = StringFields.shouldBeOneOfThem(FIELD_SECURED, Operator.EQUALS, strings);
        StringFields forFullText = StringFields.shouldBeOneOfThem(FIELD_SECURED, Operator.KEYWORD, strings);
        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(forCriteria);
        fields.add(forFullText);
        return fields;
    }
}