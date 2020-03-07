package pl.lasota.tool.sr.repository.query.field;

import pl.lasota.tool.sr.repository.query.Common;
import pl.lasota.tool.sr.repository.query.ComparisonOperator;
import pl.lasota.tool.sr.repository.query.Expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class Field extends Common implements Expression {

    abstract public Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder);

    protected final ComparisonOperator comparisonOperator;
    protected final String path;

    public Field(String path,  ComparisonOperator comparisonOperator) {
        this.path = path;
        this.comparisonOperator = comparisonOperator;
    }

}
