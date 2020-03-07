package pl.lasota.tool.sr.repository.search.specification;

import pl.lasota.tool.sr.repository.CommonSpecification;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.query.Predicatable;
import pl.lasota.tool.sr.repository.query.sort.Sortable;

import javax.persistence.criteria.*;

public class SearchCriteriaSpecification<MODEL extends EntityBase>
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
