package pl.lasota.tool.orm.repository.delete.criteria;

import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.CommonSpecification;
import pl.lasota.tool.orm.repository.DistributeCriteriaFactory;
import pl.lasota.tool.orm.repository.delete.SpecificationDelete;

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
