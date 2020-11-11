package pl.lasota.database.repository.query.field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class NumberRangeField extends RangeField<Number> {

    public NumberRangeField(String path, Number min, Number max) {
        super(path, min, max);

    }

    @Override
    public Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        Path objectPath = generatePath(path, root, model);

        if (objectPath.getJavaType().getName().equals("java.lang.Double")
                || objectPath.getJavaType().getName().equals("double")) {
            return criteriaBuilder.between(
                    objectPath,
                    min.doubleValue(),
                    max.doubleValue());
        }
        if (objectPath.getJavaType().getName().equals("java.lang.Integer")
                || objectPath.getJavaType().getName().equals("int")) {
            return criteriaBuilder.between(
                    objectPath,
                    min.intValue(),
                    max.intValue());
        }
        if (objectPath.getJavaType().getName().equals("java.lang.Long")
                || objectPath.getJavaType().getName().equals("long")) {
            return criteriaBuilder.between(
                    objectPath,
                    min.longValue(),
                    max.longValue());
        }
        if (objectPath.getJavaType().getName().equals("java.lang.Short") ||
                objectPath.getJavaType().getName().equals("short")) {
            return criteriaBuilder.between(
                    objectPath,
                    min.shortValue(),
                    max.shortValue());
        }

        return criteriaBuilder.between(
                objectPath,
                min.floatValue(),
                max.floatValue());
    }
}
