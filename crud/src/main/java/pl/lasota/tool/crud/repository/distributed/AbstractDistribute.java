package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.repository.MapperFields;
import pl.lasota.tool.crud.repository.field.CriteriaField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class AbstractDistribute<MODEL> {

    private final List<CriteriaField<?>> fields;
    private final MapperFields<MODEL> mapperFields;

    public AbstractDistribute(List<CriteriaField<?>> fields, MapperFields<MODEL> mapperFields) {
        this.fields = fields;
        this.mapperFields = mapperFields;
    }

    public AbstractDistribute<MODEL> set(Map<String, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        new SetDistribute<>(fields, mapperFields).process(criteriaUpdate, modelRoot);
        return this;
    }

    public AbstractDistribute<MODEL> and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        new AndDistribute<>(fields, mapperFields).process(predicates, root, cb);
        return this;
    }

    public AbstractDistribute<MODEL> or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        new OrDistribute<>(fields, mapperFields).process(predicates, root, cb);
        return this;
    }

    public AbstractDistribute<MODEL> sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {
        new SortDistribute<>(fields, mapperFields).process(orders, root, cb);
        return this;
    }

    public AbstractDistribute<MODEL> swap(Class<MODEL> modelClass) {
        List<CriteriaField<?>> processed = new SwapDistribute<MODEL>(fields, modelClass).processed();
        return new AbstractDistribute<>(processed, mapperFields);
    }
}
