package pl.lasota.tool.sr.repository.update.specification;

import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.CommonSpecification;
import pl.lasota.tool.sr.repository.DistributeCriteriaFactory;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

public class UpdateCriteriaSpecification<MODEL extends EntityBase> extends CommonSpecification<MODEL> implements SpecificationUpdate<MODEL> {
    private DistributeCriteriaFactory<MODEL> distributeFieldFactory;

    public UpdateCriteriaSpecification(DistributeCriteriaFactory<MODEL> distributeFieldFactory) {
        super(distributeFieldFactory);
        this.distributeFieldFactory = distributeFieldFactory;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder) {
        Map<Path<Object>, Object> stringStringMap = new HashMap<>();

        distributeFieldFactory.set(stringStringMap, root);

        stringStringMap.forEach(criteriaUpdate::set);

        return super.toPredicate(root, criteriaBuilder);
    }

}
