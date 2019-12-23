package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.repository.search.Specification;
import pl.lasota.tool.crud.serach.field.CriteriaField;
import pl.lasota.tool.crud.serach.field.Field;

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
