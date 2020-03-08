package pl.lasota.tool.sr.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<MODEL extends BasicEntity> {

    Predicate toPredicate(Class<MODEL> modelClass, Root<MODEL> root, CriteriaBuilder criteriaBuilder);

}
