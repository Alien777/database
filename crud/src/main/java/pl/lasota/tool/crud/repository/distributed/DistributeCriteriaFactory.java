package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.repository.field.CriteriaField;
import pl.lasota.tool.crud.repository.mapping.SortMapping;
import pl.lasota.tool.crud.repository.mapping.PredicatesMapping;
import pl.lasota.tool.crud.repository.mapping.SetMapping;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

public class DistributeCriteriaFactory<MODEL> {

    private final List<CriteriaField<?>> fields;
    private final SetMapping<MODEL> modelSetMapping;
    private final SortMapping<MODEL> modelSortMapping;
    private final PredicatesMapping<MODEL> modelPredicatesMapping;

    public DistributeCriteriaFactory(List<CriteriaField<?>> fields,
                                     SetMapping<MODEL> modelSetMapping,
                                     SortMapping<MODEL> modelSortMapping,
                                     PredicatesMapping<MODEL> modelPredicatesMapping) {
        this.fields = fields;
        this.modelSetMapping = modelSetMapping;
        this.modelSortMapping = modelSortMapping;
        this.modelPredicatesMapping = modelPredicatesMapping;

    }

    public DistributeCriteriaFactory<MODEL> set(Map<Path, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        new SetDistribute<>(modelSetMapping, modelRoot, criteriaUpdate).process(fields);
        return this;
    }

    public DistributeCriteriaFactory<MODEL> and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        new AndDistribute<>(modelPredicatesMapping, root, predicates, cb).process(fields);
        return this;
    }

    public DistributeCriteriaFactory<MODEL> or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        new OrDistribute<>(modelPredicatesMapping, root, predicates, cb).process(fields);
        return this;
    }

    public DistributeCriteriaFactory<MODEL> sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {
        new SortDistribute<>(modelSortMapping, root, orders, cb).process(fields);
        return this;
    }

}
