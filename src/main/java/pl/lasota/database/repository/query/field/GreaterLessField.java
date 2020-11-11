package pl.lasota.database.repository.query.field;

import lombok.Getter;
import lombok.ToString;
import pl.lasota.database.repository.query.ComparisonOperator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ToString(callSuper = true)
@Getter
public class GreaterLessField extends Field {
    private final Number value;

    public GreaterLessField(String paths, Number value, ComparisonOperator comparisonOperator) {
        super(paths, comparisonOperator);
        this.value = value;
    }

    @Override
    public Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        Path<Number> objectPath = generatePath(path, root, model);
        switch (comparisonOperator) {
            case LESS_THAN:
                return criteriaBuilder.lt(objectPath, value);
            case LESS_THAN_OR_EQUAL:
                return criteriaBuilder.le(objectPath, value);
            case GREATER_THAN:
                return criteriaBuilder.gt(objectPath, value);
            default:
                return criteriaBuilder.ge(objectPath, value);

        }
    }
}
