package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.repository.MapperFields;
import pl.lasota.tool.crud.repository.field.CriteriaField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class DistributeFactory<MODEL> {

    private final List<CriteriaField<?>> fields;
    private final MapperFields<MODEL> mapperFields;

    public DistributeFactory(List<CriteriaField<?>> fields, MapperFields<MODEL> mapperFields) {
        this.fields = fields;
        this.mapperFields = mapperFields;
    }

    public DistributeFactory<MODEL> set(Map<String, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        new SetDistribute<>(fields, mapperFields).process(criteriaUpdate, modelRoot);
        return this;
    }

    public DistributeFactory<MODEL> and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        new AndDistribute<>(fields, mapperFields).process(predicates, root, cb);
        return this;
    }

    public DistributeFactory<MODEL> or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        new OrDistribute<>(fields, mapperFields).process(predicates, root, cb);
        return this;
    }

    public DistributeFactory<MODEL> sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {
        new SortDistribute<>(fields, mapperFields).process(orders, root, cb);
        return this;
    }

    public DistributeFactory<MODEL> swap(Class<MODEL> modelClass) {
        List<CriteriaField<?>> processed = new SwapDistribute<MODEL>(fields, modelClass).processed();
        return new DistributeFactory<>(processed, mapperFields);
    }
}
