package pl.lasota.tool.crud.serach.criteria;

import pl.lasota.tool.crud.serach.field.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


public class DistributeField<MODEL> implements CriteriaSupplier<MODEL> {

    private final List<CriteriaField> fields;
    private final Mapper<MODEL> mapper;

    public DistributeField(List<CriteriaField> fields, Mapper<MODEL> mapper) {
        this.fields = fields;
        this.mapper = mapper;
    }

    @Override
    public void and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field.getCriteriaType() == CriteriaType.AND)
                .forEach(field -> mapper.map(field, predicates, root, cb));
    }

    @Override
    public void or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field.getCriteriaType() == CriteriaType.OR)
                .forEach(field -> mapper.map(field, predicates, root, cb));
    }

    @Override
    public void sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field.getCriteriaType() == CriteriaType.SORT)
                .filter(field -> field instanceof SortField)
                .map(field -> (SortField)field)
                .forEach(field -> mapper.map(field, orders, root, cb));
    }

}
