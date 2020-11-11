package pl.lasota.database.repository.query.normalize;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public interface Normalizable {

    Expression<String> normalize(Expression<String> objectPath, CriteriaBuilder criteriaBuilder);

    String normalize(String value);

}
