package pl.lasota.tool.sr.repository.query.field;

import lombok.Getter;
import lombok.ToString;
import pl.lasota.tool.sr.repository.query.ComparisonOperator;
import pl.lasota.tool.sr.repository.query.normalize.Normalizable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@ToString(callSuper = true)
@Getter
public class LikeField extends Field {
    private final String value;
    private final Normalizable[] normalizable;


    public LikeField(String paths, String value, ComparisonOperator comparisonOperator) {
        super(paths, comparisonOperator);
        this.value = value;
        this.normalizable = null;
    }

    public LikeField(String paths, String value, ComparisonOperator comparisonOperator, Normalizable... normalizable) {
        super(paths, comparisonOperator);
        this.value = value;
        this.normalizable = normalizable;
    }

    @Override
    public Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        Expression<String> objectPath = generatePath(path, root, model);
        if (normalizable != null) {
            for (Normalizable n : normalizable) {
                objectPath = n.normalize(objectPath, criteriaBuilder);
            }
        }

        switch (comparisonOperator) {
            case LIKE_LEFT:
                return criteriaBuilder.like(objectPath, "%" + value);
            case LIKE_RIGHT:
                return criteriaBuilder.like(objectPath, value + "%");
            default:
                return criteriaBuilder.like(objectPath, "%" + value + "%");
        }

    }
}
