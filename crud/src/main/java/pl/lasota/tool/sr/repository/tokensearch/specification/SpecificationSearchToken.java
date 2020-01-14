package pl.lasota.tool.sr.repository.tokensearch.specification;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;

public interface SpecificationSearchToken {

    void search(QueryBuilder queryBuilder, BooleanJunction<BooleanJunction> booleanJunction);

    Sort sort(SortContext sortContext);
}
