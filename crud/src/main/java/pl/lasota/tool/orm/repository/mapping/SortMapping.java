package pl.lasota.tool.orm.repository.mapping;

import pl.lasota.tool.orm.repository.field.SortField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

public interface SortMapping<MODEL> {

    void map(SortField field, List<Order> orders, Root<MODEL> root, CriteriaBuilder cb);
}
