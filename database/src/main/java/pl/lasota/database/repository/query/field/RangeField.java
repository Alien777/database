package pl.lasota.database.repository.query.field;

import pl.lasota.database.repository.query.ComparisonOperator;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class RangeField<T> extends Field {

    abstract public Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder);

    protected final T min;
    protected final T max;

    public RangeField(String path, T min, T max) {
        super(path, ComparisonOperator.RANGE);
        this.min = min;
        this.max = max;
    }

}
