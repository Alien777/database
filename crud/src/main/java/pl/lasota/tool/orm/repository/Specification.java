package pl.lasota.tool.orm.repository;

import pl.lasota.tool.orm.common.EntityBase;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Specification<MODEL extends EntityBase> {

    Predicate toPredicate(Root<MODEL> root,  CriteriaBuilder criteriaBuilder);

}
