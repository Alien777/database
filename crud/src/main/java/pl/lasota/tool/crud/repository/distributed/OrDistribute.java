package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.repository.CriteriaType;
import pl.lasota.tool.crud.repository.MapperFields;
import pl.lasota.tool.crud.repository.field.CriteriaField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

public class OrDistribute<MODEL> {

    private final List<CriteriaField<?>> lists;
    private final MapperFields<MODEL> mapperFields;

    public OrDistribute(List<CriteriaField<?>> lists, MapperFields<MODEL> mapperFields) {
        this.lists = lists;
        this.mapperFields = mapperFields;
    }

    public void process(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        lists.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.OR)
                .forEach(field -> mapperFields.map(field, predicates, root, cb));
    }
}
