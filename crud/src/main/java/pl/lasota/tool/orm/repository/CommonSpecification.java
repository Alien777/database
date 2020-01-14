package pl.lasota.tool.orm.repository;

import pl.lasota.tool.orm.common.EntityBase;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class CommonSpecification<MODEL extends EntityBase> implements Specification<MODEL> {

    private final DistributeCriteriaFactory<MODEL> distributeCriteriaFactory;

    public CommonSpecification(DistributeCriteriaFactory<MODEL> distributeCriteriaFactory) {
        this.distributeCriteriaFactory = distributeCriteriaFactory;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicateAndList = new LinkedList<>();
        List<Predicate> predicateOrList = new LinkedList<>();

        distributeCriteriaFactory.and(predicateAndList, root, criteriaBuilder).or(predicateOrList, root, criteriaBuilder);

        return toPredicate(predicateAndList, predicateOrList, criteriaBuilder);
    }

    private Predicate toPredicate(List<Predicate> predicateAndList, List<Predicate> predicateOrList, CriteriaBuilder criteriaBuilder) {

        Predicate[] predicatesAnd = predicateAndList.toArray(new Predicate[0]);
        Predicate[] predicateOr = predicateOrList.toArray(new Predicate[0]);

        Predicate or = criteriaBuilder.or(predicateOr);
        Predicate and = criteriaBuilder.and(predicatesAnd);

        if (predicatesAnd.length >= 1 && predicateOr.length >= 1) {
            return criteriaBuilder.or(and, or);
        } else if (predicatesAnd.length >= 1) {
            return criteriaBuilder.and(predicatesAnd);
        }

        return criteriaBuilder.or(predicateOr);
    }
}
