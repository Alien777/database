package pl.lasota.tool.sr.repository;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.sr.field.*;

public class TokenFieldMapping {


    public void map(CriteriaField<?> field, QueryBuilder queryBuilder, BooleanJunction booleanJunction, Selector selector) {

        if (field.condition().equals(Operator.BETWEEN)) {
            RangeStringField f = (RangeStringField) field;
            Range<String> range = f.getValue();
            Query above = queryBuilder.range().onField(f.getName()).above(range.getMinimum()).createQuery();
            Query below = queryBuilder.range().onField(f.getName()).below(range.getMaximum()).createQuery();

            if (above != null) {
                type(above, booleanJunction, selector);
            }

            if (below != null) {
                type(below, booleanJunction, selector);
            }
        } else {

            Query query = null;
            StringField f = (StringField) field;

            if (field.condition().equals(Operator.SIMPLE)) {
                query = queryBuilder.simpleQueryString().onField(f.getName()).matching(f.getValue()).createQuery();
            }

            if (field.condition().equals(Operator.KEYWORD)) {
                query = queryBuilder.keyword().onField(f.getName()).matching(f.getValue()).createQuery();
            }

            if (field.condition().equals(Operator.PHRASE)) {
                query = queryBuilder.phrase().onField(f.getName()).sentence(f.getValue()).createQuery();
            }

            if (field.condition().equals(Operator.GE) || field.condition().equals(Operator.GT)) {
                query = queryBuilder.range().onField(f.getName()).above(f.getValue()).createQuery();
            }

            if (field.condition().equals(Operator.LE) || field.condition().equals(Operator.LT)) {
                query = queryBuilder.range().onField(f.getName()).below(f.getValue()).createQuery();
            }
            if (query != null) {
                type(query, booleanJunction, selector);
            }
        }
    }

    private void type(Query query, BooleanJunction booleanJunction, Selector selector) {
        if (selector == Selector.SHOULD) {
            booleanJunction.should(query);
        }
        if (selector == Selector.NOT_MUST) {
            booleanJunction.must(query).not();
        }
        if (selector == Selector.MUST || selector == Selector.AND) {
            booleanJunction.must(query);
        }

    }

    public org.apache.lucene.search.Sort map(CriteriaField<?> fullTextField, SortContext sortContext) {
        org.apache.lucene.search.Sort sort = null;
        if (fullTextField instanceof SortCoordinateField) {
            SortCoordinateField f = (SortCoordinateField) fullTextField;
            pl.lasota.tool.sr.field.Sort sort1 = f.getSort();

            if (sort1.equals(pl.lasota.tool.sr.field.Sort.ASC)) {
                sort = sortContext.byDistance().onField(f.getName()).fromCoordinates(f.getValue()).asc().createSort();

            }

            if (sort1.equals(pl.lasota.tool.sr.field.Sort.DESC)) {
                sort = sortContext.byDistance().onField(f.getName()).fromCoordinates(f.getValue()).desc().createSort();

            }
        }

        if (fullTextField instanceof SortField) {
            SortField f = (SortField) fullTextField;
            if (f.getValue().equals(pl.lasota.tool.sr.field.Sort.ASC)) {
                sort = sortContext.byField(f.getName()).asc().createSort();

            }

            if (f.getValue().equals(pl.lasota.tool.sr.field.Sort.DESC)) {
                sort = sortContext.byField(f.getName()).desc().createSort();
            }

        }

        return sort;
    }
}
