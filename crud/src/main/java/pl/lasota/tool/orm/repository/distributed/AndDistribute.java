package pl.lasota.tool.orm.repository.distributed;

import pl.lasota.tool.orm.common.CriteriaType;
import pl.lasota.tool.orm.common.Processable;
import pl.lasota.tool.orm.repository.field.CriteriaField;
import pl.lasota.tool.orm.repository.mapping.PredicatesMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

public class AndDistribute<MODEL> implements Processable {

    private final PredicatesMapping<MODEL> mapperFields;
    private final Root<MODEL> root;
    private final List<Predicate> predicates;
    private final CriteriaBuilder criteriaBuilder;


    public AndDistribute(PredicatesMapping<MODEL> mapperFields, Root<MODEL> root, List<Predicate> predicates, CriteriaBuilder criteriaBuilder) {
        this.mapperFields = mapperFields;
        this.root = root;
        this.predicates = predicates;
        this.criteriaBuilder = criteriaBuilder;
    }

    @Override
    public void process(List<CriteriaField<?>> fields) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.AND)
                .forEach(field -> mapperFields.map(field, predicates, root, criteriaBuilder));

    }
}
