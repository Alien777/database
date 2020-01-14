package pl.lasota.tool.sr.repository.delete.specification;

import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.CommonSpecification;
import pl.lasota.tool.sr.repository.DistributeCriteriaFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DeleteCriteriaSpecification<MODEL extends EntityBase> extends CommonSpecification<MODEL> implements SpecificationDelete<MODEL> {

    public DeleteCriteriaSpecification(DistributeCriteriaFactory<MODEL> distributeFieldFactory) {
        super(distributeFieldFactory);
    }

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaBuilder criteriaBuilder) {
        return super.toPredicate(root, criteriaBuilder);
    }
}
