package pl.lasota.tool.sr.repository.query;

import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.repository.query.sort.Sortable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;


public class QueryCriteria implements Sortable, Predicatable {

    private final Predicatable predicate;
    private final Sortable sort;
    private final Pageable pageable;


    public QueryCriteria(Predicatable predicate, Sortable sort, Pageable pageable) {
        this.predicate = predicate;
        this.sort = sort;
        this.pageable = pageable;
    }

    @Override
    public javax.persistence.criteria.Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        return predicate.predicate(model, root, criteriaBuilder);
    }

    @Override
    public Order order(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        if (sort == null) {
            return null;
        }
        return sort.order(model, root, criteriaBuilder);
    }

    public Pageable getPageable() {
        return pageable;
    }
}
