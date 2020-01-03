package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.common.CriteriaType;
import pl.lasota.tool.crud.repository.field.CriteriaField;
import pl.lasota.tool.crud.repository.field.SetField;
import pl.lasota.tool.crud.repository.mapping.SetMapping;

import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class SetDistribute<MODEL> implements Processable {

    private final SetMapping<MODEL> setMapping;
    private final Root<MODEL> root;
    private final Map<String, Object> stringObjectMap;


    public SetDistribute(SetMapping<MODEL> setMapping, Root<MODEL> root, Map<String, Object> stringObjectMap) {
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
