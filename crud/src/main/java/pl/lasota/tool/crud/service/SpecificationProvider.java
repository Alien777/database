package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.fulltextrepository.SpecificationFullText;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.field.CriteriaField;

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
