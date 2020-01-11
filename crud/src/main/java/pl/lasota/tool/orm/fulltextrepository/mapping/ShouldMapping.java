package pl.lasota.tool.orm.fulltextrepository.mapping;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import pl.lasota.tool.orm.repository.field.CriteriaField;

public class ShouldMapping implements MappingFieldFullText {

    @Override
    public void map(CriteriaField<?> fullTextField, QueryBuilder queryBuilder, BooleanJunction booleanJunction) {

    }
}
