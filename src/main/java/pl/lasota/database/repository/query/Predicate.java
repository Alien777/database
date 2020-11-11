package pl.lasota.database.repository.query;

import lombok.Getter;
import lombok.ToString;
import pl.lasota.database.repository.query.field.Field;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ToString(callSuper = true)
@Getter
public final class Predicate implements Predicatable {

    private final Expression[] expressions;
    private final Operator operator;

    public Predicate(Expression[] expressions, Operator operator) {
        this.expressions = expressions;
        this.operator = operator;

    }

    public Predicate(Expression... expressions) {
        this.expressions = expressions;
        this.operator = null;
    }

    public Predicate(Expression parent) {
        expressions = new Expression[1];
        expressions[0] = parent;
        this.operator = null;
    }

    public Predicate(Expression parent, Operator operator) {
        expressions = new Expression[1];
        expressions[0] = parent;
        this.operator = operator;

    }

    @Override
    public javax.persistence.criteria.Predicate predicate(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        if (operator != Operator.ROOT) {
            throw new UnsupportedOperationException("Only for root");
        }

        javax.persistence.criteria.Predicate[] and = Arrays.stream(expressions)
                .filter(e -> e instanceof Predicate)
                .map(e -> (Predicate) e)
                .filter(predicate -> predicate.getOperator() == Operator.AND)
                .map(predicate -> Arrays.stream(predicate.getExpressions()).collect(Collectors.toList())).flatMap(List::stream)
                .filter(e -> e instanceof Field)
                .map(e -> (Field) e)
                .map(f -> f.predicate(model, root, criteriaBuilder))
                .toArray(javax.persistence.criteria.Predicate[]::new);

        javax.persistence.criteria.Predicate[] or = Arrays.stream(expressions)
                .filter(e -> e instanceof Predicate)
                .map(e -> (Predicate) e)
                .filter(predicate -> predicate.getOperator() == Operator.OR)
                .map(predicate -> Arrays.stream(predicate.getExpressions()).collect(Collectors.toList())).flatMap(List::stream)
                .filter(e -> e instanceof Field)
                .map(e -> (Field) e)
                .map(f -> f.predicate(model, root, criteriaBuilder))
                .toArray(javax.persistence.criteria.Predicate[]::new);

        if (or.length != 0 && and.length != 0) {
            return criteriaBuilder.and(criteriaBuilder.and(and), criteriaBuilder.or(or));
        } else if (or.length != 0) {
            return criteriaBuilder.or(or);
        } else if (and.length != 0) {
            return criteriaBuilder.and(and);
        } else if (expressions.length == 1) {
           return criteriaBuilder.and(expressions[0].predicate(model, root, criteriaBuilder));
        }


        return criteriaBuilder.and(criteriaBuilder.and(and), criteriaBuilder.or(or));
    }
}
