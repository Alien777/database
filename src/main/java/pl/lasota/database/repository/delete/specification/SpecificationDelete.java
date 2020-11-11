package pl.lasota.database.repository.delete.specification;

import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationDelete<MODEL extends BasicEntity> extends Specification<MODEL> {

    @Override
    Predicate toPredicate(Class<MODEL> modelClass, Root<MODEL> root, CriteriaBuilder criteriaBuilder);
}
