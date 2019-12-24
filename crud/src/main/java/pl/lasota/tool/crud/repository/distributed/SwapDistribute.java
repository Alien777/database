package pl.lasota.tool.crud.repository.distributed;

import pl.lasota.tool.crud.repository.annotaction.AliasColumnDiscovery;
import pl.lasota.tool.crud.repository.field.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class SwapDistribute<MODEL> {

    private final List<CriteriaField<?>> lists;
    private Class<MODEL> modelClass;

    public SwapDistribute(List<CriteriaField<?>> lists, Class<MODEL> modelClass) {
        this.lists = lists;
        this.modelClass = modelClass;
    }

    public List<CriteriaField<?>> processed() {

        AliasColumnDiscovery<MODEL> discovery = new AliasColumnDiscovery<>();

        List<SetField> setFields = lists.stream()
                .filter(field -> field instanceof SetField)
                .map(field -> (SetField) field)
                .collect(Collectors.toList());

        List<CriteriaField<?>> newFields = new LinkedList<>();
        lists.forEach(field -> {
            setFields.forEach(setField -> {
                if (field instanceof RangeStringField) {
                    RangeStringField stringField = (RangeStringField) field;
                    String discoverSet = discovery.discover(setField.getName(), modelClass);
                    String discoverField = discovery.discover(stringField.getName(), modelClass);
                    if (discoverSet.equals(discoverField)) {
                        newFields.add(new RangeStringField(discoverField, new Range<>(setField.getValue(), setField.getValue()), stringField.getCriteriaType()));
                    } else {
                        newFields.add(new RangeStringField(discoverField, stringField.getValue(), stringField.getCriteriaType()));
                    }
                } else if (field instanceof StringField) {
                    StringField stringField = (StringField) field;
                    String discoverSet = discovery.discover(setField.getName(), modelClass);
                    String discoverField = discovery.discover(stringField.getName(), modelClass);
                    if (discoverSet.equals(discoverField)) {
                        newFields.add(new StringField(discoverField, setField.getValue(), stringField.getCriteriaType(), stringField.condition()));
                    } else {
                        newFields.add(new StringField(discoverField, stringField.getValue(), stringField.getCriteriaType(), stringField.condition()));
                    }
                }
            });
        });

        return newFields;
    }
}
