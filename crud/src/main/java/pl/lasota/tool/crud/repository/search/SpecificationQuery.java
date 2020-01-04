package pl.lasota.tool.crud.repository.search;

import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.Specification;

import javax.persistence.criteria.*;

public interface SpecificationQuery<MODEL extends EntityBase> extends Specification<MODEL> {

    Predicate toPredicate(Root<MODEL> root, CriteriaQuery<MODEL> criteriaQuery, CriteriaBuilder criteriaBuilder);

}
