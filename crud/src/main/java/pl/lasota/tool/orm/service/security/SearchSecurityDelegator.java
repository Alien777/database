package pl.lasota.tool.orm.service.security;

import org.springframework.data.domain.Page;
import pl.lasota.tool.orm.common.Access;
import pl.lasota.tool.orm.common.EntitySecurity;
import pl.lasota.tool.orm.field.Condition;
import pl.lasota.tool.orm.field.Field;
import pl.lasota.tool.orm.field.StringFields;
import pl.lasota.tool.orm.security.AccessContext;
import pl.lasota.tool.orm.security.Context;
import pl.lasota.tool.orm.service.SearchService;
import pl.lasota.tool.orm.service.base.BaseFullTextSearchService;
import pl.lasota.tool.orm.service.base.BaseSearchService;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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


    private List<Field<?>> createFieldsSecured(Context context) {
        Set<AccessContext> secureds = context.getSecured();

        String[] strings = secureds.stream().map(s -> s.getName() + Access.SEPARATOR + s.getRud()).toArray(String[]::new);
        StringFields or = StringFields.or("accesses.value", Condition.EQUALS, strings);
        StringFields or1 = StringFields.or("accesses.value", Condition.KEYWORD, strings);
        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(or);
        fields.add(or1);
        return fields;
    }
}
