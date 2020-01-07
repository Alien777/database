package pl.lasota.tool.crud.repository.update.criteria;

import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;

import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

public class UpdateCriteriaSpecification<MODEL extends EntityBase> extends SearchCriteriaSpecification<MODEL> implements SpecificationUpdate<MODEL> {
    private DistributeCriteriaFactory<MODEL> distributeFieldFactory;

    public UpdateCriteriaSpecification(DistributeCriteriaFactory<MODEL> distributeFieldFactory) {
        super(distributeFieldFactory);
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
