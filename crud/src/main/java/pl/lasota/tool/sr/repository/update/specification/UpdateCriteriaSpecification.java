package pl.lasota.tool.sr.repository.update.specification;

import org.springframework.data.util.Pair;
import pl.lasota.tool.sr.repository.CriteriaFieldMapping;
import pl.lasota.tool.sr.repository.DistributeForCriteria;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.CommonSpecification;
import pl.lasota.tool.sr.field.DistributeForField;

import javax.persistence.criteria.*;

public class UpdateCriteriaSpecification<MODEL extends EntityBase> extends CommonSpecification<MODEL> implements SpecificationUpdate<MODEL> {

    public UpdateCriteriaSpecification(DistributeForCriteria distributeField, CriteriaFieldMapping<MODEL> fieldMapping) {
        super(distributeField, fieldMapping);
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder) {

        distributeField.set()
                .forEach(setField -> {
            Pair<Path<Object>, Object> map = fieldMapping.map(setField, root);
            criteriaUpdate.set(map.getFirst(), map.getSecond());
        });

        return super.toPredicate(root, criteriaBuilder);
    }

}
