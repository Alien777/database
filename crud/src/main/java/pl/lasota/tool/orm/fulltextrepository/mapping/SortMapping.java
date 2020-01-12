package pl.lasota.tool.orm.fulltextrepository.mapping;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.field.SortField;
import pl.lasota.tool.orm.field.StringField;
import pl.lasota.tool.orm.fulltextrepository.field.SortCoordinateField;

public class SortMapping implements SortFieldFullText {

    @Override
    public Sort map(CriteriaField<?> fullTextField, SortContext sortContext) {
        Sort sort = null;
        if (fullTextField instanceof SortCoordinateField) {
            SortCoordinateField f = (SortCoordinateField) fullTextField;
            pl.lasota.tool.orm.common.Sort sort1 = f.getSort();

            if (sort1.equals(pl.lasota.tool.orm.common.Sort.ASC)) {
                sort = sortContext.byDistance().onField(f.getName()).fromCoordinates(f.getValue()).asc().createSort();

            }

            if (sort1.equals(pl.lasota.tool.orm.common.Sort.DESC)) {
                sort = sortContext.byDistance().onField(f.getName()).fromCoordinates(f.getValue()).desc().createSort();

            }

        }

        if (fullTextField instanceof SortField) {
            SortField f = (SortField) fullTextField;
            if (f.getValue().equals(pl.lasota.tool.orm.common.Sort.ASC)) {
                sort = sortContext.byField(f.getName()).asc().createSort();

            }

            if (f.getValue().equals(pl.lasota.tool.orm.common.Sort.DESC)) {
                sort = sortContext.byField(f.getName()).desc().createSort();
            }

        }

        return sort;
    }
}
