package pl.lasota.tool.crud.repository.search.criteria;

import pl.lasota.tool.crud.serach.field.CriteriaField;
import pl.lasota.tool.crud.serach.field.Field;
import pl.lasota.tool.crud.serach.field.SetField;
import pl.lasota.tool.crud.serach.field.SortField;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

public class DistributeFieldFactory<MODEL> {

    private final List<Field<?>> fields;
    private final MapperFields<MODEL> mapperFields;

    public DistributeFieldFactory(List<Field<?>> fields, MapperFields<MODEL> mapperFields) {
        this.fields = fields;
        this.mapperFields = mapperFields;
    }

    public DistributeFieldFactory<MODEL> and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field instanceof CriteriaField<?>)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.AND)
                .forEach(field -> mapperFields.map(field, predicates, root, cb));
        return this;
    }


    public DistributeFieldFactory<MODEL> or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field instanceof CriteriaField<?>)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.OR)
                .forEach(field -> mapperFields.map(field, predicates, root, cb));
        return this;
    }

    public DistributeFieldFactory<MODEL> sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field instanceof SortField)
                .map(field -> (SortField) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SORT)
                .forEach(field -> mapperFields.map(field, orders, root, cb));
        return this;
    }


    public DistributeFieldFactory<MODEL> set(Map<String, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        fields.stream()
                .filter(field -> field instanceof SetField)
                .map(field -> (SetField) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SET)
                .forEach(field -> mapperFields.map(field, criteriaUpdate, modelRoot));
        return this;
    }

}
