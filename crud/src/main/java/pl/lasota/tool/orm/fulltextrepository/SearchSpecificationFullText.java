package pl.lasota.tool.orm.fulltextrepository;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.orm.fulltextrepository.distributed.DistributeFullTextFactory;


public class SearchSpecificationFullText implements SpecificationFullText {

    private final DistributeFullTextFactory distributeFactory;

    public SearchSpecificationFullText(DistributeFullTextFactory distributeFactory) {
        this.distributeFactory = distributeFactory;
    }

    @Override
    public void search(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        distributeFactory.must(queryBuilder, booleanJunction)
                .notMust(queryBuilder, booleanJunction)
                .should(queryBuilder, booleanJunction);
    }

    @Override
    public Sort sort(SortContext sortContext) {
        return distributeFactory.sort(sortContext);
    }
}
