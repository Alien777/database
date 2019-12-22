package pl.lasota.tool.crud.update;

import lombok.AllArgsConstructor;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.serach.criteria.CriteriaAndSupplier;
import pl.lasota.tool.crud.serach.criteria.CriteriaOrSupplier;
import pl.lasota.tool.crud.serach.criteria.CriteriaSetSupplier;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
public class BuildingUpdateCriteriaSpecification<MODEL> implements Specification<MODEL> {

    private final CriteriaAndSupplier<MODEL> criteriaAndSupplier;

    private final CriteriaOrSupplier<MODEL> criteriaOrSupplier;

    private final CriteriaSetSupplier<MODEL> criteriaSetSupplier;

    @Override
    public Predicate toPredicate(Root<MODEL> root, CriteriaUpdate<MODEL> criteriaUpdate, CriteriaBuilder criteriaBuilder) {
        criteriaSetSupplier.set(criteriaUpdate);

        List<Predicate> predicateAndList = new LinkedList<>();
        criteriaAndSupplier.and(predicateAndList, root, criteriaBuilder);
        Predicate[] predicatesAnd = predicateAndList.toArray(new Predicate[0]);

        List<Predicate> predicateOrList = new LinkedList<>();
        criteriaOrSupplier.or(predicateOrList, root, criteriaBuilder);
        Predicate[] predicateOr = predicateOrList.toArray(new Predicate[0]);

        Predicate or = criteriaBuilder.or(predicateOr);

        Predicate and = criteriaBuilder.and(predicatesAnd);

        if (predicatesAnd.length >= 1 && predicateOr.length >= 1) {
            return criteriaBuilder.or(and, or);
        } else if (predicatesAnd.length >= 1) {
            return criteriaBuilder.and(predicatesAnd);
        }
        return criteriaBuilder.or(predicateOr);
    }
}
