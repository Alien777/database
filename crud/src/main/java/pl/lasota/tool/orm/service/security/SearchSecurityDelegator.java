package pl.lasota.tool.orm.service.security;

import org.springframework.data.domain.Page;
import pl.lasota.tool.orm.common.Access;
import pl.lasota.tool.orm.common.EntitySecurity;
import pl.lasota.tool.orm.field.Operator;
import pl.lasota.tool.orm.field.Field;
import pl.lasota.tool.orm.field.StringFields;
import pl.lasota.tool.orm.security.AccessContext;
import pl.lasota.tool.orm.security.Context;
import pl.lasota.tool.orm.service.SearchService;
import pl.lasota.tool.orm.service.base.BaseFullTextSearchService;
import pl.lasota.tool.orm.service.base.BaseSearchService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SearchSecurityDelegator<READING, MODEL extends EntitySecurity> {

    private final SearchService<READING> searchService;

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

    List<Integer> canRead = new LinkedList<>() {{
        add(4);
        add(5);
        add(6);
        add(7);
    }};

    private List<Field<?>> createFieldsSecured(Context context) {
        Set<AccessContext> secureds = context.getSecured();

        String[] strings = secureds.stream().map(s -> {
            Stream<String> stringStream = canRead.stream().map(r -> s.getName() + Access.SEPARATOR + r);
            return stringStream.collect(Collectors.toList());
        }).collect(Collectors.toList()).stream().flatMap(Collection::stream).toArray(String[]::new);

        StringFields or = StringFields.shouldBeOneOfThem("accesses.value", Operator.EQUALS, strings);
        StringFields or1 = StringFields.shouldBeOneOfThem("accesses.value", Operator.KEYWORD, strings);
        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(or);
        fields.add(or1);
        return fields;
    }
}
