package pl.lasota.tool.crud.repository.mapping;

import pl.lasota.tool.crud.repository.field.CriteriaField;
import pl.lasota.tool.crud.repository.field.SetField;
import pl.lasota.tool.crud.repository.field.SortField;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

public interface SetMapping<MODEL> {

    void map(SetField fields, Map<String, Object> criteriaUpdate, Root<MODEL> root);
}
