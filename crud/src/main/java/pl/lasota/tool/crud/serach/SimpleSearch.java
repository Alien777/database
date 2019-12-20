package pl.lasota.tool.crud.serach;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class SimpleSearch<T, SOURCE> implements Specification<T> {

    private final ProviderSimpleSearch<T, SOURCE> providerSimpleSearch;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicateAndList = new LinkedList<>();
        providerSimpleSearch.predicateAnd(predicateAndList, root, criteriaBuilder);
        Predicate[] predicatesAnd = predicateAndList.toArray(new Predicate[0]);

        List<Predicate> predicateOrList = new LinkedList<>();
        providerSimpleSearch.predicateOr(predicateOrList, root, criteriaBuilder);
        Predicate[] predicateOr = predicateOrList.toArray(new Predicate[0]);

        Predicate or = criteriaBuilder.or(predicateOr);

        Predicate and = criteriaBuilder.and(predicatesAnd);

        if (predicatesAnd.length >= 1 && predicateOr.length >= 1) {
            return criteriaBuilder.and(and, or);
        } else if (predicatesAnd.length >= 1) {
            return criteriaBuilder.and(predicatesAnd);
        }
        return criteriaBuilder.or(predicateOr);
    }

}
