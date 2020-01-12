package pl.lasota.tool.orm.fulltextrepository.mapping;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.orm.field.CriteriaField;

public interface SortFieldFullText {

    Sort map(CriteriaField<?> fullTextField, SortContext sortContext);
}
