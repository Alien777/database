package pl.lasota.tool.sr.repository.update.specification;

import pl.lasota.tool.sr.repository.CommonSpecification;
import pl.lasota.tool.sr.repository.BasicEntity;
import pl.lasota.tool.sr.repository.query.Predicatable;
import pl.lasota.tool.sr.repository.query.Updatable;

import javax.persistence.criteria.*;
import java.util.Map;

public class UpdateCriteriaSpecification<MODEL extends BasicEntity> extends CommonSpecification<MODEL> implements SpecificationUpdate<MODEL> {

    private final Updatable updatable;

    public UpdateCriteriaSpecification(Updatable updatable, Predicatable predicatable) {
        super(predicatable);
        this.updatable = updatable;
    }

    @Override
    public Predicate toPredicate(Class<MODEL> model, Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder) {

        Map<Path<Object>, Object> update = updatable.update(model, root, criteriaBuilder);
        update.forEach(criteriaUpdate::set);
        return super.toPredicate(model, root, criteriaBuilder);
    }

}
