package pl.lasota.tool.orm.service;

import pl.lasota.tool.orm.field.Field;
import pl.lasota.tool.orm.field.CriteriaField;

import java.util.List;
import java.util.stream.Collectors;

public interface SpecificationProvider<TYPE> {
    default List<CriteriaField<?>> filter(List<Field<?>> fields) {
        return fields.stream()
                .filter(field -> field instanceof CriteriaField<?>)
                .map(field -> (CriteriaField<?>) field)
                .collect(Collectors.toList());
    }

    TYPE providerSpecification(List<Field<?>> fields);
}
