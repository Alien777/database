package pl.lasota.database.repository.query.normalize;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class AcccentNormalizer implements Normalizable {
    @Override
    public Expression<String> normalize(Expression<String> objectPath, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.function("unaccent", String.class, objectPath);
    }

    @Override
    public String normalize(String value) {
        return StringUtils.stripAccents(value);
    }

}
