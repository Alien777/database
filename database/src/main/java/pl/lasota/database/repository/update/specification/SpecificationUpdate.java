package pl.lasota.database.repository.update.specification;

import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface SpecificationUpdate<MODEL extends BasicEntity> extends Specification<MODEL> {

    Predicate toPredicate(Class<MODEL> model,Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder);

}
