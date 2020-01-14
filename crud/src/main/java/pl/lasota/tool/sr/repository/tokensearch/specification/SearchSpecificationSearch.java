package pl.lasota.tool.sr.repository.tokensearch.specification;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.sr.repository.DistributeTokenFactory;


public class SearchSpecificationSearch implements SpecificationSearchToken {

    private final DistributeTokenFactory distributeFactory;

    public SearchSpecificationSearch(DistributeTokenFactory distributeFactory) {
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
