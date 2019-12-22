package pl.lasota.tool.crud.serach.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

public interface CriteriaSortSupplier<MODEL> {

    void sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder criteriaBuilder);
}
