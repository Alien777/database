package pl.lasota.tool.orm.fulltextrepository.distributed;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import pl.lasota.tool.orm.common.CriteriaType;
import pl.lasota.tool.orm.common.Processable;
import pl.lasota.tool.orm.fulltextrepository.mapping.MappingFieldFullText;
import pl.lasota.tool.orm.repository.field.CriteriaField;

import java.util.List;
import java.util.Objects;

public class NotMustDistribute implements Processable {

    private final MappingFieldFullText mappingFieldFullText;
    private final QueryBuilder queryBuilder;
    private final BooleanJunction booleanJunction;


    public NotMustDistribute(MappingFieldFullText mappingFieldFullText, QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        this.mappingFieldFullText = mappingFieldFullText;
        this.queryBuilder = queryBuilder;
        this.booleanJunction = booleanJunction;
    }


    @Override
    public void process(List<CriteriaField<?>> fields) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.NOT)
                .forEach(field -> mappingFieldFullText.map(field, queryBuilder, booleanJunction));
    }
}
