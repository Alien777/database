package pl.lasota.tool.crud.repository.delete.criteria;

import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.delete.SpecificationDelete;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

public class DeleteCriteriaSpecification<MODEL extends EntityBase> extends SearchCriteriaSpecification<MODEL> implements SpecificationDelete<MODEL> {
    private DistributeCriteriaFactory<MODEL> distributeFieldFactory;

    public DeleteCriteriaSpecification(DistributeCriteriaFactory<MODEL> distributeFieldFactory) {
        super(distributeFieldFactory);
        this.distributeFieldFactory = distributeFieldFactory;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder) {
        Map<Path, Object> stringStringMap = new HashMap<>();

        distributeFieldFactory.set(stringStringMap, root);

        return super.toPredicate(root, criteriaBuilder);
    }

}
