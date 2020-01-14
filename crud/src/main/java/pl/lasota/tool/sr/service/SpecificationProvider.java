package pl.lasota.tool.sr.service;

import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.field.CriteriaField;

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