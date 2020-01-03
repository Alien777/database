package pl.lasota.tool.crud.fulltextrepository.distributed;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import pl.lasota.tool.crud.common.CriteriaType;
import pl.lasota.tool.crud.fulltextrepository.mapping.MappingFieldFullText;
import pl.lasota.tool.crud.repository.field.CriteriaField;

import java.util.List;
import java.util.Objects;

public class ShouldDistribute<MODEL> {

    private final List<CriteriaField<?>> fields;

    private MappingFieldFullText mappingFieldFullText;

    public ShouldDistribute(List<CriteriaField<?>> fields, MappingFieldFullText mappingFieldFullText) {
        this.fields = fields;
        this.mappingFieldFullText = mappingFieldFullText;
    }

    public void process(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SHOULD)
                .forEach(field -> mappingFieldFullText.map(field, queryBuilder, booleanJunction));
    }

}