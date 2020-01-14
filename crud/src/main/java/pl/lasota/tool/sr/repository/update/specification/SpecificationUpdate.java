package pl.lasota.tool.sr.repository.update.specification;

import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationUpdate<MODEL extends EntityBase> extends Specification<MODEL> {

    Predicate toPredicate(Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder);

}
