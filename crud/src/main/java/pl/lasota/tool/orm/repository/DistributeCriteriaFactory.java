package pl.lasota.tool.orm.repository;

import org.springframework.data.util.Pair;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.field.Operator;
import pl.lasota.tool.orm.field.Selector;
import pl.lasota.tool.orm.field.SortField;
import pl.lasota.tool.orm.field.SetField;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DistributeCriteriaFactory<MODEL> {

    private final List<CriteriaField<?>> fields;
    private final CriteriaFieldMapping<MODEL> mapping;

    public DistributeCriteriaFactory(List<CriteriaField<?>> fields, CriteriaFieldMapping<MODEL> mapping) {
        this.fields = fields.stream().filter(c ->
        {
            Operator condition = c.condition();
            return condition == Operator.LIKE
                    || condition == Operator.LIKE_L
                    || condition == Operator.LIKE_P
                    || condition == Operator.SET
                    || condition == Operator.EQUALS
                    || condition == Operator.BETWEEN
                    || condition == Operator.SORT
                    || condition == Operator.GE
                    || condition == Operator.GT
                    || condition == Operator.LE
                    || condition == Operator.LT;

        }).collect(Collectors.toList());
        this.mapping = mapping;
    }

    public DistributeCriteriaFactory<MODEL> set(Map<Path<Object>, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        fields.stream()
                .filter(field -> field instanceof SetField)
                .map(field -> (SetField) field)
                .filter(field -> field.getSelector() == Selector.SET)
                .forEach(field -> {
                    Pair<Path<Object>, Object> map = mapping.map(field, modelRoot);
                    if (map != null) {
                        criteriaUpdate.put(map.getFirst(), map.getSecond());
                    }
                });
        return this;
    }

    public DistributeCriteriaFactory<MODEL> and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.AND)
                .forEach(c -> predicates.addAll(mapping.map(c, root, cb)));
        return this;
    }

    public DistributeCriteriaFactory<MODEL> or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.OR)
                .forEach(field -> predicates.addAll(mapping.map(field, root, cb)));
        return this;
    }

    public DistributeCriteriaFactory<MODEL> sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field instanceof SortField)
                .map(field -> (SortField) field)
                .filter(field -> field.getSelector() == Selector.SORT)
                .forEach(field -> orders.add(mapping.map(field, root, cb)));
        return this;
    }

}
