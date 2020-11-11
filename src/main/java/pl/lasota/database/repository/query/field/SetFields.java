package pl.lasota.database.repository.query.field;

import lombok.Getter;
import lombok.ToString;
import pl.lasota.database.repository.query.Common;
import pl.lasota.database.repository.query.Updatable;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Map;

@ToString(callSuper = true)
@Getter
public class SetFields extends Common implements Updatable {

    private SetField[] setFields;

    public SetFields(SetField... setFields) {
        this.setFields = setFields;
    }

    @Override
    public Map<Path<Object>, Object> update(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {
        Map<Path<Object>, Object> map = new HashMap<>();
        for (SetField setField : setFields) {
            Path<Object> objectPath = generatePath(setField.getPath(), root, model);
            map.put(objectPath, setField.getValue());
        }
        return map;
    }
}
