package pl.lasota.tool.crud.fulltextrepository.distributed;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;

public class DistributeFullTextFactory {
    public DistributeFullTextFactory must(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {

        return this;
    }

    public DistributeFullTextFactory notMust(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {

        return this;
    }

    public DistributeFullTextFactory should(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {

        return this;
    }

    public Sort sort(SortContext sortContext) {

        return null;
    }
}
