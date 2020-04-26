package pl.lasota.tool.sr.repository.query.normalize;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class UpperCaseNormalizer implements Normalizable {
    @Override
    public Expression<String> normalize(Expression<String> objectPath, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.upper(objectPath);
    }

    @Override
    public String normalize(String value) {
        return value.toUpperCase();
    }
}
