package pl.lasota.tool.crud.fulltextrepository.mapping;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.crud.repository.field.CriteriaField;

public class SortField implements SortFieldFullText {

    @Override
    public Sort map(CriteriaField<?> fullTextField, SortContext sortContext) {
        return null;
    }
}
