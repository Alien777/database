package pl.lasota.tool.crud.repository.delete.criteria;

import pl.lasota.tool.crud.repository.DistributeFieldFactory;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.delete.SpecificationDelete;
import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;
import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.Map;

public class DeleteCriteriaSpecification<MODEL> extends SearchCriteriaSpecification<MODEL> implements SpecificationDelete<MODEL> {
    private DistributeFieldFactory<MODEL> distributeFieldFactory;

    public DeleteCriteriaSpecification(DistributeFieldFactory<MODEL> distributeFieldFactory) {
        super(distributeFieldFactory);
        this.distributeFieldFactory = distributeFieldFactory;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder) {
        Map<String, Object> stringStringMap = new HashMap<>();

        distributeFieldFactory.set(stringStringMap, root);

        return super.toPredicate(root, criteriaBuilder);
    }

}
