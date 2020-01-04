package pl.lasota.tool.crud.repository.search.criteria;

import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

public class SearchCriteriaSpecification<MODEL extends EntityBase> implements SpecificationQuery<MODEL> {

    private final DistributeCriteriaFactory<MODEL> distributeFactory;

    public SearchCriteriaSpecification(DistributeCriteriaFactory<MODEL> distributeCriteriaFactory) {
        this.distributeFactory = distributeCriteriaFactory;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaQuery<MODEL> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Order> orders = new LinkedList<>();
        List<Predicate> predicateAndList = new LinkedList<>();
        List<Predicate> predicateOrList = new LinkedList<>();

        distributeFactory.sort(orders, root, criteriaBuilder)
                .and(predicateAndList, root, criteriaBuilder)
                .or(predicateOrList, root, criteriaBuilder);

        sort(criteriaQuery, orders);
        return search(predicateAndList, predicateOrList, criteriaBuilder);
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder) {
        return toPredicate(root, null, criteriaBuilder);
    }

    private void sort(CriteriaQuery<MODEL> criteriaQuery, List<Order> orders) {
        Order[] ordersArray = orders.toArray(new Order[0]);
        if (ordersArray.length > 0 && criteriaQuery != null) {
            criteriaQuery.orderBy(ordersArray);
        }
    }

    private Predicate search(List<Predicate> predicateAndList, List<Predicate> predicateOrList, CriteriaBuilder criteriaBuilder) {

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
