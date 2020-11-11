package pl.lasota.database.repository.search.specification;

import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.CommonSpecification;
import pl.lasota.database.repository.query.Predicatable;
import pl.lasota.database.repository.query.sort.Sortable;

import javax.persistence.criteria.*;

public class SearchCriteriaSpecification<MODEL extends BasicEntity>
        extends CommonSpecification<MODEL> implements SpecificationQuery<MODEL> {


    private final Sortable sortable;

    public SearchCriteriaSpecification(Predicatable predicatable, Sortable sortable) {
        super(predicatable);
        this.sortable = sortable;
    }

    @Override
    public Predicate toPredicate(Class<MODEL> model, Root<MODEL> root, CriteriaQuery<MODEL> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        Order order = sortable.order(model, root, criteriaBuilder);
        if (order != null) {
            criteriaQuery.orderBy(order);
        }
        return super.toPredicate(model, root, criteriaBuilder);
    }
}
