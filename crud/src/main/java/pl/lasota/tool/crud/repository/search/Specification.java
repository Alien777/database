package pl.lasota.tool.crud.repository.search;

import javax.persistence.criteria.*;

public interface Specification<MODEL> {

    Predicate toPredicate(Root<MODEL> root,  CriteriaBuilder criteriaBuilder);

}
