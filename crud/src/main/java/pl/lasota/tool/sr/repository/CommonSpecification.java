package pl.lasota.tool.sr.repository;

import pl.lasota.tool.sr.repository.query.Predicatable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CommonSpecification<MODEL extends EntityBase> implements Specification<MODEL> {

    private final Predicatable predicatable;

    public CommonSpecification(Predicatable predicatable) {
        this.predicatable = predicatable;
    }

    @Override
    public Predicate toPredicate(Class<MODEL> model, Root<MODEL> root, CriteriaBuilder criteriaBuilder) {
        return predicatable.predicate(model, root, criteriaBuilder);
    }
}
