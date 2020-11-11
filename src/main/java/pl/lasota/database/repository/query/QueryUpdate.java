package pl.lasota.database.repository.query;

import javax.persistence.criteria.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Map;

public class QueryUpdate implements Updatable, Predicatable {

    private final Predicatable predicate;
    private final Updatable updatable;

    public QueryUpdate(Predicatable predicate, Updatable updatable) {
        this.predicate = predicate;
        this.updatable = updatable;
    }

    @Override
    public javax.persistence.criteria.Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        return predicate.predicate(model, root, criteriaBuilder);
    }


    @Override
    public Map<Path<Object>, Object> update(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        return updatable.update(model, root, criteriaBuilder);
    }
}
