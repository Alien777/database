package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.field.CriteriaField;

import java.util.List;
import java.util.stream.Collectors;

public interface SpecificationProvider<MODEL> {
    default List<CriteriaField<?>> filter(List<Field<?>> fields) {
        return fields.stream()
                .filter(field -> field instanceof CriteriaField<?>)
                .map(field -> (CriteriaField<?>) field)
                .collect(Collectors.toList());
    }

     Specification<MODEL> providerSpecification(List<Field<?>> fields);
}
