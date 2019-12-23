package pl.lasota.tool.crud.repository.search.criteria;

import pl.lasota.tool.crud.serach.field.CriteriaField;
import pl.lasota.tool.crud.serach.field.SetField;
import pl.lasota.tool.crud.serach.field.SortField;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

public interface MapperFields<MODEL> {

    void map(CriteriaField<?> field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb);

    void map(SortField field, List<Order> orders, Root<MODEL> root, CriteriaBuilder cb);

    void map(SetField fields, Map<String, Object> criteriaUpdate, Root<MODEL> root);
}
