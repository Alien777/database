package pl.lasota.tool.crud.common;

import pl.lasota.tool.crud.repository.field.CriteriaField;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface Processable {

    void process(List<CriteriaField<?>> fields);

}


