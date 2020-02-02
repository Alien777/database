package pl.lasota.tool.sr.repository.tokensearch.specification;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.sr.field.Selector;
import pl.lasota.tool.sr.field.SortField;
import pl.lasota.tool.sr.repository.DistributeForTokenizer;
import pl.lasota.tool.sr.repository.TokenFieldMapping;


public class SearchSpecificationSearch implements SpecificationSearchToken {

    private final DistributeForTokenizer distributeFactory;
    private TokenFieldMapping tokenFieldMapping;

    public SearchSpecificationSearch(DistributeForTokenizer distributeFactory, TokenFieldMapping tokenFieldMapping) {
        this.distributeFactory = distributeFactory;
        this.tokenFieldMapping = tokenFieldMapping;
    }

    @Override
    public void search(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        distributeFactory.must().forEach(criteriaField ->
                tokenFieldMapping.map(criteriaField, queryBuilder, booleanJunction, Selector.MUST));

        distributeFactory.notMust().forEach(criteriaField ->
                tokenFieldMapping.map(criteriaField, queryBuilder, booleanJunction, Selector.NOT_MUST));

        distributeFactory.should().forEach(criteriaField ->
                tokenFieldMapping.map(criteriaField, queryBuilder, booleanJunction, Selector.SHOULD));
    }

    @Override
    public Sort sort(SortContext sortContext) {
        SortField sort = distributeFactory.sort();
        return tokenFieldMapping.map(sort, sortContext);
    }
}
