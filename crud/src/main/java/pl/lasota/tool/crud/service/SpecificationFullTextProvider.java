package pl.lasota.tool.crud.service;

import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.field.CriteriaField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.stream.Collectors;

public interface SpecificationFullTextProvider<MODEL extends EntityBase> extends Specification<MODEL> {
    default List<CriteriaField<?>> filter(List<Field<?>> fields) {
        return fields.stream()
                .filter(field -> field instanceof CriteriaField<?>)
                .map(field -> (CriteriaField<?>) field)
                .collect(Collectors.toList());
    }

    default Specification<MODEL> providerSpecification(List<Field<?>> fields) {
        return new Specification<MODEL>() {
            @Override
            public Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.equal(root.get("id"), 0);
            }
        };
    }

      Specification<MODEL> providerSpecification(List<Field<?>> fields) {

}
