package pl.lasota.tool.crud.repository.update.criteria;

import pl.lasota.tool.crud.repository.distributed.AbstractDistribute;
import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

public class UpdateCriteriaSpecification<MODEL> extends SearchCriteriaSpecification<MODEL> implements SpecificationUpdate<MODEL> {
    private AbstractDistribute<MODEL> distributeFieldFactory;

    public UpdateCriteriaSpecification(AbstractDistribute<MODEL> distributeFieldFactory, Class<MODEL> modelClass) {
        super(distributeFieldFactory.swap(modelClass));
        this.distributeFieldFactory = distributeFieldFactory;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder) {
        Map<String, Object> stringStringMap = new HashMap<>();

        distributeFieldFactory.set(stringStringMap, root);

        stringStringMap.forEach(criteriaUpdate::set);

        return super.toPredicate(root, criteriaBuilder);
    }

}
