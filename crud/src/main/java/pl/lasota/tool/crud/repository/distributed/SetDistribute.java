package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.repository.CriteriaType;
import pl.lasota.tool.crud.repository.MapperFields;
import pl.lasota.tool.crud.repository.field.CriteriaField;
import pl.lasota.tool.crud.repository.field.SetField;

import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Map;

public class SetDistribute<MODEL> {

    private final List<CriteriaField<?>> lists;
    private final MapperFields<MODEL> mapperFields;

    public SetDistribute(List<CriteriaField<?>> lists, MapperFields<MODEL> mapperFields) {
        this.lists = lists;
        this.mapperFields = mapperFields;
    }

    public void process(Map<String, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        lists.stream()
                .filter(field -> field instanceof SetField)
                .map(field -> (SetField) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SET)
                .forEach(field -> mapperFields.map(field, criteriaUpdate, modelRoot));
    }
}
