package pl.lasota.tool.sr.repository.query.field;

import lombok.Getter;
import lombok.ToString;
import pl.lasota.tool.sr.repository.query.ComparisonOperator;
import pl.lasota.tool.sr.repository.query.normalize.Normalizable;

import javax.persistence.criteria.*;

@ToString(callSuper = true)
@Getter
public class EqualField extends Field {
    private Object value;
    private final Normalizable[] normalizable;

    public EqualField(String paths, Object value, ComparisonOperator comparisonOperator) {
        super(paths, comparisonOperator);
        this.value = value;
        this.normalizable = null;
    }

    public EqualField(String paths, Object value, ComparisonOperator comparisonOperator, Normalizable... normalizable) {
        super(paths, comparisonOperator);
        this.value = value;
        this.normalizable = normalizable;
    }

    @Override
    public Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        Expression<String> objectPath = generatePath(path, root, model);
        if (normalizable != null && value instanceof String) {
            for (Normalizable n : normalizable) {
                objectPath = n.normalize(objectPath, criteriaBuilder);
                value = n.normalize((String) value);
            }
        }

        if (comparisonOperator == ComparisonOperator.NOT_EQUAL) {
            return criteriaBuilder.notEqual(objectPath, value);
        }
        return criteriaBuilder.equal(objectPath, value);
    }
}
