package pl.lasota.tool.sr.repository.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

public class QueryDelete implements Predicatable {

    private final Predicatable predicate;

    public QueryDelete(Predicatable predicate) {
        this.predicate = predicate;
    }

    @Override
    public javax.persistence.criteria.Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        return predicate.predicate(model, root, criteriaBuilder);
    }
}
