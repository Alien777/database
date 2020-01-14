package pl.lasota.tool.sr.repository;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.sr.field.CriteriaField;
import pl.lasota.tool.sr.field.Operator;
import pl.lasota.tool.sr.field.Selector;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class DistributeTokenFactory {
    private final List<CriteriaField<?>> fields;
    private final TokenFieldMapping tokenFieldMapping;

    public DistributeTokenFactory(List<CriteriaField<?>> fields, TokenFieldMapping tokenFieldMapping) {
        this.fields = fields.stream().filter(c ->
        {
            Operator condition = c.condition();
            return condition == Operator.KEYWORD
                    || condition == Operator.BETWEEN
                    || condition == Operator.PHRASE
                    || condition == Operator.SIMPLE
                    || condition == Operator.SORT;
        }).collect(Collectors.toList());

        this.tokenFieldMapping = tokenFieldMapping;

    }

    public DistributeTokenFactory must(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.MUST || field.getSelector() == Selector.AND)
                .forEach(field -> tokenFieldMapping.map(field, queryBuilder, booleanJunction,Selector.MUST));
        return this;
    }

    public DistributeTokenFactory notMust(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.NOT_MUST)
                .forEach(field -> tokenFieldMapping.map(field, queryBuilder, booleanJunction, Selector.NOT_MUST));
        return this;
    }

    public DistributeTokenFactory should(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.SHOULD)
                .forEach(field -> tokenFieldMapping.map(field, queryBuilder, booleanJunction,Selector.SHOULD));
        return this;
    }

    public Sort sort(SortContext sortContext) {
        Optional<? extends CriteriaField<?>> first = fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.SORT)
                .findFirst();

        if (first.isEmpty()) {
            return tokenFieldMapping.map(null, sortContext);
        }

        return tokenFieldMapping.map(first.get(), sortContext);
    }
}
