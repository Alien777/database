package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.common.CriteriaType;
import pl.lasota.tool.crud.repository.field.CriteriaField;
import pl.lasota.tool.crud.repository.mapping.PredicatesMapping;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Objects;

public class OrDistribute<MODEL> implements Processable {

    private final PredicatesMapping<MODEL> predicatesMapping;
    private final Root<MODEL> root;
    private final List<Predicate> predicates;
    private final CriteriaBuilder criteriaBuilder;

    public OrDistribute(PredicatesMapping<MODEL> predicatesMapping,  Root<MODEL> root, List<Predicate> predicates, CriteriaBuilder criteriaBuilder) {
        this.predicatesMapping = predicatesMapping;
        this.root = root;
        this.predicates = predicates;
        this.criteriaBuilder = criteriaBuilder;
    }

    @Override
    public void process(List<CriteriaField<?>> fields) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.OR)
                .forEach(field -> predicatesMapping.map(field, predicates, root, criteriaBuilder));

    }
}
