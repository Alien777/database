package pl.lasota.tool.orm.repository.update;

import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationUpdate<MODEL extends EntityBase> extends Specification<MODEL> {

    Predicate toPredicate(Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder);

}
