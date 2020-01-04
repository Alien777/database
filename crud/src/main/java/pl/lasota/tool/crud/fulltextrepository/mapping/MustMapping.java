package pl.lasota.tool.crud.fulltextrepository.mapping;

import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import pl.lasota.tool.crud.repository.field.CriteriaField;

public class MustMapping implements MappingFieldFullText {

    @Override
    public void map(CriteriaField<?> fullTextField, QueryBuilder queryBuilder, BooleanJunction booleanJunction) {

    }

}
