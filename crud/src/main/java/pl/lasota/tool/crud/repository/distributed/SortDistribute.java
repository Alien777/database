package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.repository.CriteriaType;
import pl.lasota.tool.crud.repository.MapperFields;
import pl.lasota.tool.crud.repository.field.CriteriaField;
import pl.lasota.tool.crud.repository.field.SortField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

public class SortDistribute<MODEL> {

    private final List<CriteriaField<?>> fields;
    private final MapperFields<MODEL> mapperFields;

    public SortDistribute(List<CriteriaField<?>> fields, MapperFields<MODEL> mapperFields) {
        this.fields = fields;
        this.mapperFields = mapperFields;
    }

    public void process(List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field instanceof SortField)
                .map(field -> (SortField) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SORT)
                .forEach(field -> mapperFields.map(field, orders, root, cb));

    }
}
