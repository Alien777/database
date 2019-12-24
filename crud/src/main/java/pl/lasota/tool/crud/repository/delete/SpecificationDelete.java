package pl.lasota.tool.crud.repository.delete;

import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationDelete<MODEL> extends SpecificationQuery<MODEL>, Specification<MODEL> {

    Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder);

}
