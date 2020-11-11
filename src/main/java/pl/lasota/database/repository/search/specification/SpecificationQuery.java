package pl.lasota.database.repository.search.specification;

import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationQuery<MODEL extends BasicEntity> extends Specification<MODEL> {

    Predicate toPredicate(Class<MODEL> model, Root<MODEL> root, CriteriaQuery<MODEL> criteriaQuery, CriteriaBuilder criteriaBuilder);

}
