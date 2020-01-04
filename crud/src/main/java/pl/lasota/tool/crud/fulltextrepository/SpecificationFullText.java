package pl.lasota.tool.crud.fulltextrepository;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.search.SpecificationQuery;

public interface SpecificationFullText {

    void search(QueryBuilder queryBuilder, BooleanJunction<BooleanJunction> booleanJunction);

    Sort sort(SortContext sortContext);
}
