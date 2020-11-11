package pl.lasota.database.repository.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

public interface Updatable {

    Map<Path<Object>, Object> update(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder);

}
