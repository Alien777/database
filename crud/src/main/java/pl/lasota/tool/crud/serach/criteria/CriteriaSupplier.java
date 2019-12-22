package pl.lasota.tool.crud.serach.criteria;

import javax.persistence.criteria.*;
import java.util.List;

public interface CriteriaSupplier<MODEL> {

    void and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder criteriaBuilder);

    void or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder criteriaBuilder);

    void sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder criteriaBuilder);
}
