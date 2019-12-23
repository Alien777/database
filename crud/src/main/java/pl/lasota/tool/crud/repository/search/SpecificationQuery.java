package pl.lasota.tool.crud.repository.search;

import javax.persistence.criteria.*;

public interface SpecificationQuery<MODEL> {

    Predicate toPredicate(Root<MODEL> root, CriteriaQuery<MODEL> criteriaQuery, CriteriaBuilder criteriaBuilder);

}
