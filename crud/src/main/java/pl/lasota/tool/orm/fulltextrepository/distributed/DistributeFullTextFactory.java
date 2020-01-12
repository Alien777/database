package pl.lasota.tool.orm.fulltextrepository.distributed;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.fulltextrepository.mapping.MappingFieldFullText;
import pl.lasota.tool.orm.fulltextrepository.mapping.SortFieldFullText;

import java.util.List;

public class DistributeFullTextFactory {
    private final List<CriteriaField<?>> filter;
    private final MappingFieldFullText mustMapping;
    private final MappingFieldFullText notMustMapping;
    private final MappingFieldFullText shouldMapping;
    private final SortFieldFullText sortFieldFullText;

    public DistributeFullTextFactory(List<CriteriaField<?>> filter, MappingFieldFullText mustMapping, MappingFieldFullText notMustMapping, MappingFieldFullText shouldMapping, SortFieldFullText sortFieldFullText) {
        this.filter = filter;
        this.mustMapping = mustMapping;
        this.notMustMapping = notMustMapping;
        this.shouldMapping = shouldMapping;
        this.sortFieldFullText = sortFieldFullText;
    }

    public DistributeFullTextFactory must(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        new MustDistribute(mustMapping, queryBuilder, booleanJunction).process(filter);
        return this;
    }

    public DistributeFullTextFactory notMust(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        new NotMustDistribute(notMustMapping, queryBuilder, booleanJunction).process(filter);
        return this;
    }

    public DistributeFullTextFactory should(QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        new ShouldDistribute(shouldMapping, queryBuilder, booleanJunction).process(filter);
        return this;
    }

    public Sort sort(SortContext sortContext) {
        SortDistribute sortDistribute = new SortDistribute(sortFieldFullText, sortContext);
        sortDistribute.process(filter);
        return sortDistribute.getSort();
    }
}
