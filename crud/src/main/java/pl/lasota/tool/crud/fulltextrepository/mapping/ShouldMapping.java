package pl.lasota.tool.crud.fulltextrepository.mapping;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.crud.repository.field.CriteriaField;

public class ShouldMapping implements MappingFieldFullText {

    @Override
    public void map(CriteriaField<?> fullTextField, QueryBuilder queryBuilder, BooleanJunction booleanJunction) {

    }
}
