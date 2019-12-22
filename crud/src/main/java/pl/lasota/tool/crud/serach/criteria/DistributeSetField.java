package pl.lasota.tool.crud.serach.criteria;

import pl.lasota.tool.crud.serach.field.CriteriaField;
import pl.lasota.tool.crud.serach.field.SortField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;


public class DistributeSetField<MODEL> implements CriteriaSetSupplier<MODEL> {

    private final List<CriteriaField> fields;
    private final Mapper<MODEL> mapper;

    public DistributeSetField(List<CriteriaField> fields, Mapper<MODEL> mapper) {
        this.fields = fields;
        this.mapper = mapper;
    }

    @Override
    public void set(CriteriaUpdate criteriaUpdate) {
        fields.stream()
                .filter(field -> field.getCriteriaType() == CriteriaType.SORT)
                .filter(field -> field instanceof SortField)
                .map(field -> (SortField) field)
                .forEach(field -> mapper.map(field, criteriaUpdate));
    }
}
