package pl.lasota.tool.crud.repository.mapping;


import pl.lasota.tool.crud.common.AliasColumnDiscovery;
import pl.lasota.tool.crud.common.Sort;
import pl.lasota.tool.crud.common.Condition;
import pl.lasota.tool.crud.repository.field.*;

import javax.persistence.criteria.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class FieldMapping<MODEL> implements SortMapping<MODEL>, PredicatesMapping<MODEL>,
        SetMapping<MODEL> {

    private final static Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+([.|,]\\d+)?");
    private final AliasColumnDiscovery<MODEL> aliasColumnDiscovery;

    public FieldMapping(Class<MODEL> bindableJavaType) {
        aliasColumnDiscovery = new AliasColumnDiscovery<>(bindableJavaType);
    }

    @Override
    public void map(CriteriaField<?> field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) {

        if (field instanceof RangeStringField) {
            try {
                create((RangeStringField) field, predicates, root, cb);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (field instanceof StringField) {
            try {
                create((StringField) field, predicates, root, cb);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void map(SortField field, List<Order> orders, Root<MODEL> root, CriteriaBuilder cb) {

        String[] split = field.getName().split("\\.");
        Path<Object> objectPath = root.get(split[0]);

        for (int i = 1; i < split.length; i++) {
            objectPath = objectPath.get(split[i]);
        }

        Order order;
        if (field.getValue() == Sort.ASC) {
            order = cb.asc(objectPath);
        } else {
            order = cb.desc(objectPath);
        }
        orders.add(order);

    }

    @Override
    public void map(SetField field, Map<Path<Object>, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        try {
            create(field, criteriaUpdate, modelRoot);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void create(SetField field, Map<Path<Object>, Object> criteriaUpdate, Root<MODEL> root) throws ParseException {
        String[] split = field.getName().split("\\.");
        Path<Object> objectPath = root.get(split[0]);

        for (int i = 1; i < split.length; i++) {
            objectPath = objectPath.get(split[i]);
        }

        Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
        criteriaUpdate.put(objectPath, o);
    }

    private void create(RangeStringField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Predicate predicate = null;

        String[] split = field.getName().split("\\.");
        Path<Double> doublePath = root.get(split[0]);
        Path<Float> floatPath = root.get(split[0]);
        Path<Short> shortPath = root.get(split[0]);
        Path<Integer> intPath = root.get(split[0]);
        Path<Long> longPath = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            doublePath = doublePath.get(split[i]);
            floatPath = floatPath.get(split[i]);
            shortPath = shortPath.get(split[i]);
            intPath = intPath.get(split[i]);
            longPath = longPath.get(split[i]);
        }


        if (field.condition() == Condition.BETWEEN) {
            Range<String> range = field.getValue();
            if (!isNumeric(range.getMinimum()) && !isNumeric(range.getMaximum())) {
                return;
            }

            Number first = convertStringToNumber(range.getMinimum());
            Number second = convertStringToNumber(range.getMaximum());
            if (isNumeric(range.getMinimum()) && isNumeric(range.getMaximum())) {
                if (doublePath.getJavaType().getName().equals("java.lang.Double")) {
                    predicate = cb.between(
                            doublePath,
                            first.doubleValue(),
                            second.doubleValue());
                }
                if (intPath.getJavaType().getName().equals("java.lang.Integer")) {
                    predicate = cb.between(
                            intPath,
                            first.intValue(),
                            second.intValue());
                }
                if (longPath.getJavaType().getName().equals("java.lang.Long")) {
                    predicate = cb.between(
                            longPath,
                            first.longValue(),
                            second.longValue());
                }
                if (shortPath.getJavaType().getName().equals("java.lang.Short")) {
                    predicate = cb.between(
                            shortPath,
                            first.shortValue(),
                            second.shortValue());
                }
                if (floatPath.getJavaType().getName().equals("java.lang.Float")) {
                    predicate = cb.between(
                            floatPath,
                            first.floatValue(),
                            second.floatValue());
                }
            }
        }

        if (predicate != null) {
            predicates.add(predicate);
        }
    }

    private String mapAlias(String name) {
        return aliasColumnDiscovery.discover(name);
    }


    private void create(StringField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Predicate predicate = null;
        String[] split = field.getName().split("\\.");
        Path<Object> objectPath = root.get(split[0]);
        Path<String> stringPath = root.get(split[0]);
        Path<Number> numberPath = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            stringPath = stringPath.get(split[i]);
            objectPath = objectPath.get(split[i]);
            numberPath = numberPath.get(split[i]);
        }

        switch (field.condition()) {
            case EQUALS:
                Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
                if (o != null) {
                    predicate = cb.equal(objectPath, o);
                }
                break;
            case LIKE:
                if (stringPath.getJavaType().getName().equals("java.lang.String"))
                    predicate = cb.like(stringPath, "%" + field.getValue() + "%");

                break;
            case LIKE_L:
                if (stringPath.getJavaType().getName().equals("java.lang.String"))
                    predicate = cb.like(stringPath, field.getValue() + "%");

                break;
            case LIKE_P:
                if (stringPath.getJavaType().getName().equals("java.lang.String"))
                    predicate = cb.like(stringPath, "%" + field.getValue());
                break;
            case GE:
                if (isNumeric(field.getValue()) && isFromNumberClass(numberPath.getJavaType().getName()))
                    predicate = cb.ge(numberPath, convertStringToNumber(field.getValue()));
                break;
            case GT:
                if (isNumeric(field.getValue()) && isFromNumberClass(numberPath.getJavaType().getName()))
                    predicate = cb.gt(numberPath, convertStringToNumber(field.getValue()));
                break;
            case LE:
                if (isNumeric(field.getValue()) && isFromNumberClass(numberPath.getJavaType().getName()))
                    predicate = cb.le(numberPath, convertStringToNumber(field.getValue()));
                break;
            case LT:
                if (isNumeric(field.getValue()) && isFromNumberClass(numberPath.getJavaType().getName()))
                    predicate = cb.lt(numberPath, convertStringToNumber(field.getValue()));
        }
        if (predicate != null) {
            predicates.add(predicate);
        }
    }


    private <T> Object convertClass(String aClass, String value) throws ParseException {
        if (isFromNumberClass(aClass) && isNumeric(value)) {
            return NumberFormat.getInstance().parse(value.replace(".", ","));
        }
        boolean s = aClass.equals("java.lang.String");
        if (s) {
            return value;
        }

        return null;
    }


    private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return NUMBER_PATTERN.matcher(strNum).matches();
    }

    private boolean isFromNumberClass(String nameClass) {
        return nameClass.equals("java.lang.Double")
                || nameClass.equals("java.lang.Number")
                || nameClass.equals("java.lang.Integer")
                || nameClass.equals("java.lang.Long")
                || nameClass.equals("java.lang.Short")
                || nameClass.equals("java.lang.Float");
    }

    private Number convertStringToNumber(String value) throws ParseException {
        return NumberFormat.getInstance().parse(value.replace(".", ","));
    }
}

