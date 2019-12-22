package pl.lasota.tool.crud.serach.criteria;

import pl.lasota.tool.crud.serach.field.CriteriaField;
import pl.lasota.tool.crud.serach.field.SortField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


public class DistributeAndField<MODEL> implements CriteriaAndSupplier<MODEL> {

    private final List<CriteriaField> fields;
    private final Mapper<MODEL> mapper;

    public DistributeAndField(List<CriteriaField> fields, Mapper<MODEL> mapper) {
        this.fields = fields;
        this.mapper = mapper;
    }

    @Override
    public void and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field.getCriteriaType() == CriteriaType.AND)
                .forEach(field -> mapper.map(field, predicates, root, cb));
    }
}
