package pl.lasota.tool.crud.repository.mapping;

import pl.lasota.tool.crud.repository.field.CriteriaField;
import pl.lasota.tool.crud.repository.field.SetField;
import pl.lasota.tool.crud.repository.field.SortField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public interface SortMapping<MODEL> {

    void map(SortField field, List<Order> orders, Root<MODEL> root, CriteriaBuilder cb);
}
