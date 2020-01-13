package pl.lasota.tool.orm.repository.delete.criteria;

import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.delete.SpecificationDelete;
import pl.lasota.tool.orm.repository.DistributeCriteriaFactory;
import pl.lasota.tool.orm.repository.search.criteria.SearchCriteriaSpecification;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

public class DeleteCriteriaSpecification<MODEL extends EntityBase> extends SearchCriteriaSpecification<MODEL> implements SpecificationDelete<MODEL> {

    public DeleteCriteriaSpecification(DistributeCriteriaFactory<MODEL> distributeFieldFactory) {
        super(distributeFieldFactory);
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder) {
        return super.toPredicate(root, criteriaBuilder);
    }

}
