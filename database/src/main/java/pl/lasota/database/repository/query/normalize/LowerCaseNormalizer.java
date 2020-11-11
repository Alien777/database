package pl.lasota.database.repository.query.normalize;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class LowerCaseNormalizer implements Normalizable {
    @Override
    public Expression<String> normalize(Expression<String> objectPath, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.lower(objectPath);
    }

    @Override
    public String normalize(String value) {
        return value.toLowerCase();
    }
}
