package pl.lasota.tool.sr.field;

import pl.lasota.tool.sr.repository.DistributeForCriteria;
import pl.lasota.tool.sr.repository.DistributeForTokenizer;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DistributeForField implements DistributeForCriteria, DistributeForTokenizer {

    private final List<CriteriaField<?>> fields;

    public DistributeForField(List<CriteriaField<?>> fields) {
        this.fields = fields;
    }

    public final List<SetField> set() {
        return fields.stream()
                .filter(field -> field instanceof SetField)
                .map(field -> (SetField) field)
                .filter(field -> field.getSelector() == Selector.SET)
                .collect(Collectors.toList());
    }

    public final List<CriteriaField<?>> and() {

        return fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.AND)
                .collect(Collectors.toList());
    }

    public final List<CriteriaField<?>> or() {
        return fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.OR)
                .collect(Collectors.toList());
    }

    public final SortField sort() {
        return fields.stream()
                .filter(field -> field instanceof SortField)
                .map(field -> (SortField) field)
                .filter(field -> field.getSelector() == Selector.SORT)
                .findFirst().orElse(null);
    }

    public List<CriteriaField<?>> must() {

        return fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.MUST || field.getSelector() == Selector.AND)
                .collect(Collectors.toList());
    }

    public List<CriteriaField<?>> notMust() {

        return fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.NOT_MUST)
                .collect(Collectors.toList());
    }

    public List<CriteriaField<?>> should() {
        return fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getSelector() == Selector.SHOULD)
                .collect(Collectors.toList());
    }
}
