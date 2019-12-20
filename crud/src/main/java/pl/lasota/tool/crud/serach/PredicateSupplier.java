package pl.lasota.tool.crud.serach;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface PredicateSupplier<T> {

    void predicateAnd(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder);

    void predicateOr(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder);
}
