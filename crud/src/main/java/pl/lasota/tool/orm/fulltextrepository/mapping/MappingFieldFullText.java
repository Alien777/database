package pl.lasota.tool.orm.fulltextrepository.mapping;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import pl.lasota.tool.orm.field.CriteriaField;

public interface MappingFieldFullText {

    void map(CriteriaField<?> fullTextField, QueryBuilder queryBuilder, BooleanJunction booleanJunction);
}
