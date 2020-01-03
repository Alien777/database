package pl.lasota.tool.crud.fulltextrepository.mapping;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.crud.repository.field.CriteriaField;

public interface SortFieldFullText {

    Sort map(CriteriaField<?> fullTextField, SortContext sortContext);
}
