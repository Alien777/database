package pl.lasota.tool.orm.repository.distributed;

import pl.lasota.tool.orm.field.CriteriaType;
import pl.lasota.tool.orm.common.Processable;
import pl.lasota.tool.orm.field.CriteriaField;
import pl.lasota.tool.orm.repository.field.SetField;
import pl.lasota.tool.orm.repository.mapping.SetMapping;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class SetDistribute<MODEL> implements Processable {

    private final SetMapping<MODEL> setMapping;
    private final Root<MODEL> root;
    private final Map< Path<Object>, Object> stringObjectMap;


    public SetDistribute(SetMapping<MODEL> setMapping, Root<MODEL> root, Map<Path<Object>, Object> stringObjectMap) {
        this.setMapping = setMapping;
        this.root = root;
        this.stringObjectMap = stringObjectMap;
    }

    @Override
    public void process(List<CriteriaField<?>> fields) {
        fields.stream()
                .filter(field -> field instanceof SetField)
                .map(field -> (SetField) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SET)
                .forEach(field -> setMapping.map(field, stringObjectMap, root));

    }

}
