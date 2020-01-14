package pl.lasota.tool.orm.repository.delete;

import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationDelete<MODEL extends EntityBase> extends Specification<MODEL> {

    @Override
    Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder);
}
