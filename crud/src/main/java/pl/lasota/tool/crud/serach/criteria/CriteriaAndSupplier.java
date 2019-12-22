package pl.lasota.tool.crud.serach.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface CriteriaAndSupplier<MODEL> {

    void and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder criteriaBuilder);
}
