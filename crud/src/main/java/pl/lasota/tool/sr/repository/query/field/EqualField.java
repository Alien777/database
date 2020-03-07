package pl.lasota.tool.sr.repository.query.field;

import lombok.Getter;
import lombok.ToString;
import pl.lasota.tool.sr.repository.query.ComparisonOperator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ToString(callSuper = true)
@Getter
public class EqualField extends Field {
    private Object value;

    public EqualField(String paths, Object value, ComparisonOperator comparisonOperator) {
        super(paths, comparisonOperator);
        this.value = value;
    }

    @Override
    public Predicate predicate(Class model, Root root, CriteriaBuilder criteriaBuilder) {
        Path<Object> objectPath = generatePath(path, root, model);
        if (comparisonOperator == ComparisonOperator.NOT_EQUAL) {
            return criteriaBuilder.notEqual(objectPath, value);
        }
        return criteriaBuilder.equal(objectPath, value);
    }
}
