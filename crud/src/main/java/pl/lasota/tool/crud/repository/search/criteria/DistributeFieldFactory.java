package pl.lasota.tool.crud.repository.search.criteria;

import pl.lasota.tool.crud.repository.annotaction.AliasColumnDiscovery;
import pl.lasota.tool.crud.serach.field.*;

import javax.persistence.criteria.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class DistributeFieldFactory<MODEL> {

    private final List<CriteriaField<?>> fields;
    private final MapperFields<MODEL> mapperFields;
    private final Class<MODEL> modelClass;


    public DistributeFieldFactory(List<CriteriaField<?>> fields, MapperFields<MODEL> mapperFields, Class<MODEL> modelClass) {
        this.fields = fields;
        this.mapperFields = mapperFields;
        this.modelClass = modelClass;
    }

    public DistributeFieldFactory<MODEL> and(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.AND)
                .forEach(field -> mapperFields.map(field, predicates, root, cb));
        return this;
    }


    public DistributeFieldFactory<MODEL> or(List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(Objects::nonNull)
                .map(field -> (CriteriaField<?>) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.OR)
                .forEach(field -> mapperFields.map(field, predicates, root, cb));
        return this;
    }

    public DistributeFieldFactory<MODEL> sort(List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {
        fields.stream()
                .filter(field -> field instanceof SortField)
                .map(field -> (SortField) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SORT)
                .forEach(field -> mapperFields.map(field, orders, root, cb));
        return this;
    }


    public DistributeFieldFactory<MODEL> set(Map<String, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        fields.stream()
                .filter(field -> field instanceof SetField)
                .map(field -> (SetField) field)
                .filter(field -> field.getCriteriaType() == CriteriaType.SET)
                .forEach(field -> mapperFields.map(field, criteriaUpdate, modelRoot));
        return this;
    }

    public DistributeFieldFactory<MODEL> swap() {

        AliasColumnDiscovery<MODEL> discovery = new AliasColumnDiscovery<>();

        List<SetField> setFields = fields.stream()
                .filter(field -> field instanceof SetField)
                .map(field -> (SetField) field)
                .collect(Collectors.toList());


        List<CriteriaField<?>> newFields = new LinkedList<>();
        fields.forEach(field -> {
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

        return new DistributeFieldFactory<>(newFields, mapperFields, modelClass);
    }
}
