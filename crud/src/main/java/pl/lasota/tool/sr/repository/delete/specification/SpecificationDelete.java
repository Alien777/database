package pl.lasota.tool.sr.repository.delete.specification;

import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationDelete<MODEL extends EntityBase> extends Specification<MODEL> {

    @Override
    Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder);
}
