package pl.lasota.tool.orm.fulltextrepository.mapping;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.orm.repository.field.CriteriaField;

public class SortMapping implements SortFieldFullText {

    @Override
    public Sort map(CriteriaField<?> fullTextField, SortContext sortContext) {
        return null;
    }
}
