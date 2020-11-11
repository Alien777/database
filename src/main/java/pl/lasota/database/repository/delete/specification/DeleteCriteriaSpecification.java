package pl.lasota.database.repository.delete.specification;

import pl.lasota.database.repository.CommonSpecification;
import pl.lasota.database.repository.BasicEntity;
import pl.lasota.database.repository.query.Predicatable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class DeleteCriteriaSpecification<MODEL extends BasicEntity> extends CommonSpecification<MODEL> implements SpecificationDelete<MODEL> {

    public DeleteCriteriaSpecification(Predicatable predicatable) {
        super(predicatable);
    }

    @Override
    public Predicate toPredicate(Class<MODEL> model, Root<MODEL> root, CriteriaBuilder criteriaBuilder) {
        return super.toPredicate(model, root, criteriaBuilder);
    }
}
