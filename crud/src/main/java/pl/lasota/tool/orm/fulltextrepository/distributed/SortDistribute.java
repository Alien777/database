package pl.lasota.tool.orm.fulltextrepository.distributed;

import org.apache.lucene.search.Sort;
import org.hibernate.search.query.dsl.sort.SortContext;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.field.CriteriaType;
import pl.lasota.tool.orm.common.Processable;
import pl.lasota.tool.orm.fulltextrepository.mapping.SortFieldFullText;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SortDistribute implements Processable {

    private final SortFieldFullText sortFieldFullText;
    private final SortContext sortContext;
    private Optional<? extends CriteriaField<?>> first = Optional.empty();

    public SortDistribute(SortFieldFullText sortFieldFullText, SortContext sortContext) {
        this.sortFieldFullText = sortFieldFullText;
        this.sortContext = sortContext;
    }

    @Override
    public void process(List<CriteriaField<?>> fields) {
        first = fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SORT)
                .findFirst();
    }

    public Sort getSort() {
        if (first.isPresent()) {
            return sortFieldFullText.map(first.get(), sortContext);
        }
        return sortFieldFullText.map(null, sortContext);
    }
}
