package pl.lasota.tool.crud.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<MODEL> {
    Predicate toPredicate(Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder);
}
