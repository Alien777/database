package pl.lasota.tool.orm.repository.search;

import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationQuery<MODEL extends EntityBase> extends Specification<MODEL> {

    Predicate toPredicate(Root<MODEL> root, CriteriaQuery<MODEL> criteriaQuery, CriteriaBuilder criteriaBuilder);

}
