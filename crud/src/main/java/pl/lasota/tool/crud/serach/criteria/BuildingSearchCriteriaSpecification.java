package pl.lasota.tool.crud.serach.criteria;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public final class BuildingSearchCriteriaSpecification<MODEL> implements Specification<MODEL> {


    private final CriteriaAndSupplier<MODEL> criteriaAndSupplier;

    private final CriteriaOrSupplier<MODEL> criteriaOrSupplier;

    private final CriteriaSortSupplier<MODEL> criteriaSortSupplier;

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Order> orders = new LinkedList<>();
        criteriaSortSupplier.sort(orders,root,criteriaBuilder);
        Order[] ordersArray = orders.toArray(new Order[0]);
        if(ordersArray.length>0) {
            query.orderBy(ordersArray);
        }

        List<Predicate> predicateAndList = new LinkedList<>();
        criteriaAndSupplier.and(predicateAndList, root, criteriaBuilder);
        Predicate[] predicatesAnd = predicateAndList.toArray(new Predicate[0]);

        List<Predicate> predicateOrList = new LinkedList<>();
        criteriaOrSupplier.or(predicateOrList, root, criteriaBuilder);
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
