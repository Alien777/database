package pl.lasota.tool.crud.serach.criteria;

import pl.lasota.tool.crud.serach.field.CriteriaField;
import pl.lasota.tool.crud.serach.field.SortField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;


public class DistributeOrField<MODEL> implements CriteriaOrSupplier<MODEL> {

    private final List<CriteriaField> fields;
    private final Mapper<MODEL> mapper;

    public DistributeOrField(List<CriteriaField> fields, Mapper<MODEL> mapper) {
        this.fields = fields;
        this.mapper = mapper;
    }

    @Override
    public void or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field.getCriteriaType() == CriteriaType.OR)
                .forEach(field -> mapper.map(field, predicates, root, cb));
    }

}
