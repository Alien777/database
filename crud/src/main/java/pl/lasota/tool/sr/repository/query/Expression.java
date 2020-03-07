package pl.lasota.tool.sr.repository.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface Expression {
    Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder);
}
