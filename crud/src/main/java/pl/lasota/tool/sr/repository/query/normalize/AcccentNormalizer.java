package pl.lasota.tool.sr.repository.query.normalize;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class AcccentNormalizer implements Normalizable {
    @Override
    public Expression<String> normalize(Expression<String> objectPath, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.function("unaccent", String.class, objectPath);
    }
}
