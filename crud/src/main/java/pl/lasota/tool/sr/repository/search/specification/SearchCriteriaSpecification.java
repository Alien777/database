package pl.lasota.tool.sr.repository.search.specification;

import pl.lasota.tool.sr.field.definition.SortField;
import pl.lasota.tool.sr.repository.CommonSpecification;
import pl.lasota.tool.sr.repository.CriteriaFieldMapping;
import pl.lasota.tool.sr.repository.DistributeForCriteria;
import pl.lasota.tool.sr.repository.EntityBase;

import javax.persistence.criteria.*;

public class SearchCriteriaSpecification<MODEL extends EntityBase>
        extends CommonSpecification<MODEL> implements SpecificationQuery<MODEL> {


    public SearchCriteriaSpecification(DistributeForCriteria distributeField, CriteriaFieldMapping<MODEL> fieldMapping) {
        super(distributeField, fieldMapping);
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaQuery<MODEL> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        SortField sort = distributeField.sort();

        if (sort != null) {
            Order map = fieldMapping.map(sort, root, criteriaBuilder);
            criteriaQuery.orderBy(map);
        }
        return super.toPredicate(root, criteriaBuilder);
    }
}
