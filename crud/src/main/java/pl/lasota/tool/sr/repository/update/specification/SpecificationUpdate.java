package pl.lasota.tool.sr.repository.update.specification;

import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationUpdate<MODEL extends BasicEntity> extends Specification<MODEL> {

    Predicate toPredicate(Class<MODEL> model,Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder);

}
