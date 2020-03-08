package pl.lasota.tool.sr.repository.delete.specification;

import pl.lasota.tool.sr.repository.CommonSpecification;
import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.query.Predicatable;

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
