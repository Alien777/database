package pl.lasota.tool.sr.repository.search.specification;

import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.CommonSpecification;
import pl.lasota.tool.sr.repository.DistributeCriteriaFactory;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

public class SearchCriteriaSpecification<MODEL extends EntityBase>
        extends CommonSpecification<MODEL> implements SpecificationQuery<MODEL> {

    private final DistributeCriteriaFactory<MODEL> distributeCriteriaFactory;

    public SearchCriteriaSpecification(DistributeCriteriaFactory<MODEL> distributeCriteriaFactory) {
        super(distributeCriteriaFactory);
        this.distributeCriteriaFactory = distributeCriteriaFactory;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaQuery<MODEL> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Order> orders = new LinkedList<>();
        distributeCriteriaFactory.sort(orders, root, criteriaBuilder);
        Order[] ordersArray = orders.toArray(new Order[0]);
        if (ordersArray.length > 0 && criteriaQuery != null) {
            criteriaQuery.orderBy(ordersArray);
        }

        return super.toPredicate(root, criteriaBuilder);
    }
}
