package pl.lasota.tool.sr.service.security;

import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.StringFields;
import pl.lasota.tool.sr.security.Access;
import pl.lasota.tool.sr.security.Context;
import pl.lasota.tool.sr.security.EntitySecurity;
import pl.lasota.tool.sr.service.UpdateService;
import pl.lasota.tool.sr.service.base.BaseUpdateService;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateSecurityService<MODEL extends EntitySecurity> {

    private static final String FIELD_SECURED = "accesses.value";
    private final UpdateService searchService;
    private final ProvidingRules providingRules;


    public UpdateSecurityService(BaseUpdateService<MODEL> searchService, ProvidingRules providingRules) {
        this.searchService = searchService;
        this.providingRules = providingRules;
    }

    public List<Long> update(List<Field<?>> source, Context context) {
        source.addAll(createFieldsSecured(context));
        return searchService.update(source);
    }


    private List<Field<?>> createFieldsSecured(Context context) {

        String[] strings = context.getSecured().stream().map(s -> {
            Stream<String> stringStream = providingRules.forCanDelete().stream().map(r -> s.getName() + Access.SEPARATOR + r);
            return stringStream.collect(Collectors.toList());
        }).collect(Collectors.toList()).stream().flatMap(Collection::stream).toArray(String[]::new);

        StringFields forCriteria = StringFields.shouldBeOneOfThem(FIELD_SECURED, Operator.EQUALS, strings);
        LinkedList<Field<?>> fields = new LinkedList<>();
        fields.add(forCriteria);
        return fields;
    }
}
