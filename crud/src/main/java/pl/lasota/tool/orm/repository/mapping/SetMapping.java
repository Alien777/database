package pl.lasota.tool.orm.repository.mapping;

import pl.lasota.tool.orm.repository.field.SetField;

import javax.persistence.criteria.*;
import java.util.Map;

public interface SetMapping<MODEL> {

    void map(SetField fields, Map<String, Object> criteriaUpdate, Root<MODEL> root);
}
