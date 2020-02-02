package pl.lasota.tool.sr.repository;

import pl.lasota.tool.sr.field.DistributeForField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.LinkedList;
import java.util.List;

public class CommonSpecification<MODEL extends EntityBase> implements Specification<MODEL> {

    protected final DistributeForCriteria distributeField;
    protected final CriteriaFieldMapping<MODEL> fieldMapping;

    public CommonSpecification(DistributeForCriteria distributeField, CriteriaFieldMapping<MODEL> fieldMapping) {
        this.distributeField = distributeField;
        this.fieldMapping = fieldMapping;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicateAndList = new LinkedList<>();
        List<Predicate> predicateOrList = new LinkedList<>();

        distributeField.and().forEach(field -> predicateAndList.addAll(fieldMapping.map(field, root, criteriaBuilder)));
        distributeField.or().forEach(field -> predicateOrList.addAll(fieldMapping.map(field, root, criteriaBuilder)));

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
