package pl.lasota.tool.orm.repository.distributed;

import pl.lasota.tool.orm.field.CriteriaType;
import pl.lasota.tool.orm.common.Processable;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.field.SortField;
import pl.lasota.tool.orm.repository.mapping.SortMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;

@lombok.Data(staticConstructor="of")
public class SortDistribute<MODEL> implements Processable {

    private final SortMapping<MODEL> mapperFields;
    private final Root<MODEL> root;
    private final List<Order> orders;
    private final CriteriaBuilder criteriaBuilder;

    public SortDistribute(SortMapping<MODEL> sortMapping, Root<MODEL> root, List<Order> orders, CriteriaBuilder criteriaBuilder) {
        this.mapperFields = sortMapping;
        this.root = root;
        this.orders = orders;
        this.criteriaBuilder = criteriaBuilder;
    }

    @Override
    public void process(List<CriteriaField<?>> fields) {
        fields.stream()
                .filter(field -> field instanceof SortField)
                .map(field -> (SortField) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SORT)
                .forEach(field -> mapperFields.map(field, orders, root, criteriaBuilder));

    }
}
