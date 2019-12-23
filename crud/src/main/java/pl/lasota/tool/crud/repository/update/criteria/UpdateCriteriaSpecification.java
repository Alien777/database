package pl.lasota.tool.crud.repository.update.criteria;

import pl.lasota.tool.crud.repository.search.criteria.DistributeFieldFactory;
import pl.lasota.tool.crud.repository.search.criteria.SearchCriteriaSpecification;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;

import javax.persistence.criteria.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class UpdateCriteriaSpecification<MODEL> extends SearchCriteriaSpecification<MODEL> implements SpecificationUpdate<MODEL> {
    private DistributeFieldFactory<MODEL> distributeFieldFactory;

    public UpdateCriteriaSpecification(DistributeFieldFactory<MODEL> distributeFieldFactory) {
        super(distributeFieldFactory);
        this.distributeFieldFactory = distributeFieldFactory;
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateAndList = new LinkedList<>();
        List<Predicate> predicateOrList = new LinkedList<>();
        Map<String, Object> stringStringMap = new HashMap<>();

        distributeFieldFactory.and(predicateAndList, root, criteriaBuilder)
                .or(predicateOrList, root, criteriaBuilder)
                .set(stringStringMap, root);

        stringStringMap.forEach(criteriaUpdate::set);

        return super.search(predicateAndList, predicateOrList, criteriaBuilder);
    }
}
