package pl.lasota.tool.crud.serach.criteria;

import pl.lasota.tool.crud.serach.field.CriteriaField;
import pl.lasota.tool.crud.serach.field.SortField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface Mapper<MODEL> {

    void map(CriteriaField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb);

    void map(SortField field, List<Order> orders, Root<MODEL> root, CriteriaBuilder cb);
}
