package pl.lasota.tool.orm.fulltextrepository.mapping;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import pl.lasota.tool.orm.field.*;

public class ShouldMapping implements MappingFieldFullText {

    @Override
    public void map(CriteriaField<?> field, QueryBuilder queryBuilder, BooleanJunction booleanJunction) {
        if (field.condition().equals(Operator.SIMPLE)) {
            StringField f = (StringField) field;
            Query query = queryBuilder.simpleQueryString().onField(f.getName()).matching(f.getValue()).createQuery();
            booleanJunction.should(query);
        }

        if (field.condition().equals(Operator.KEYWORD)) {
            StringField f = (StringField) field;
            Query query = queryBuilder.keyword().onField(f.getName()).matching(f.getValue()).createQuery();
            booleanJunction.should(query);
        }

        if (field.condition().equals(Operator.PHRASE)) {
            StringField f = (StringField) field;
            Query query = queryBuilder.phrase().onField(f.getName()).sentence(f.getValue()).createQuery();
            booleanJunction.should(query);
        }

        if (field.condition().equals(Operator.BETWEEN)) {
            RangeStringField f = (RangeStringField) field;
            Range<String> range = f.getValue();
            Query above = queryBuilder.range().onField(f.getName()).above(range.getMinimum()).createQuery();
            Query below = queryBuilder.range().onField(f.getName()).below(range.getMaximum()).createQuery();

            booleanJunction.should(above);
            booleanJunction.should(below);
        }

        if (field.condition().equals(Operator.GE) ||  field.condition().equals(Operator.GT) ) {
            StringField f = (StringField) field;
            Query above = queryBuilder.range().onField(f.getName()).above(f.getValue()).createQuery();
            booleanJunction.should(above);
        }

        if (field.condition().equals(Operator.LE) ||  field.condition().equals(Operator.LT) ) {
            StringField f = (StringField) field;
            Query below = queryBuilder.range().onField(f.getName()).below(f.getValue()).createQuery();
            booleanJunction.should(below);
        }
    }
}
