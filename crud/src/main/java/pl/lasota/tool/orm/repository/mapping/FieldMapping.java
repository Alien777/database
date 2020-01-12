package pl.lasota.tool.orm.repository.mapping;


import pl.lasota.tool.orm.common.AliasColumnDiscovery;
import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.common.EntitySecurity;
import pl.lasota.tool.orm.field.*;
import pl.lasota.tool.orm.common.Sort;
import pl.lasota.tool.orm.reflection.UtilsReflection;
import pl.lasota.tool.orm.repository.field.*;

import javax.persistence.criteria.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class FieldMapping<MODEL> implements SortMapping<MODEL>, PredicatesMapping<MODEL>,
        SetMapping<MODEL> {

    private final static Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+([.|,]\\d+)?");
    private final AliasColumnDiscovery<MODEL> aliasColumnDiscovery;
    private Class<MODEL> model;

    public FieldMapping(Class<MODEL> model) {
        aliasColumnDiscovery = new AliasColumnDiscovery<>(model);
        this.model = model;
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
        Path<Object> objectPath = generatePath(split, root);

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
        Path<Object> objectPath = generatePath(split, root);

        Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
        criteriaUpdate.put(objectPath, o);
    }

    private void create(RangeStringField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Predicate predicate = null;

        String[] split = field.getName().split("\\.");
        Path<Double> doublePath = generatePath(split, root);
        Path<Float> floatPath = generatePath(split, root);
        Path<Short> shortPath = generatePath(split, root);
        Path<Integer> intPath = generatePath(split, root);
        Path<Long> longPath = generatePath(split, root);


        if (field.condition() == Condition.BETWEEN) {
            Range<String> range = field.getValue();
            if (!isNumeric(range.getMinimum()) && !isNumeric(range.getMaximum())) {
                return;
            }

            Number first = convertStringToNumber(range.getMinimum());
            Number second = convertStringToNumber(range.getMaximum());
            if (isNumeric(range.getMinimum()) && isNumeric(range.getMaximum())) {
                if (doublePath.getJavaType().getName().equals("java.lang.Double")
                        || floatPath.getJavaType().getName().equals("double")) {
                    predicate = cb.between(
                            doublePath,
                            first.doubleValue(),
                            second.doubleValue());
                }
                if (intPath.getJavaType().getName().equals("java.lang.Integer")
                        || floatPath.getJavaType().getName().equals("int")) {
                    predicate = cb.between(
                            intPath,
                            first.intValue(),
                            second.intValue());
                }
                if (longPath.getJavaType().getName().equals("java.lang.Long")
                        || floatPath.getJavaType().getName().equals("long")) {
                    predicate = cb.between(
                            longPath,
                            first.longValue(),
                            second.longValue());
                }
                if (shortPath.getJavaType().getName().equals("java.lang.Short") ||
                        floatPath.getJavaType().getName().equals("short")) {
                    predicate = cb.between(
                            shortPath,
                            first.shortValue(),
                            second.shortValue());
                }
                if (floatPath.getJavaType().getName().equals("java.lang.Float") ||
                        floatPath.getJavaType().getName().equals("float")) {
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


    private <T> Path<T> generatePath(final String[] path, final Root<MODEL> root) {
        Path<T> main;

        Class<?> aClass = UtilsReflection.typeOfFields(model, path, 0);
        if (aClass != null && isCollectionClass(aClass.getTypeName())) {
            main = root.join(path[0]);
        } else {
            main = root.get(path[0]);
        }

        for (int i = 1; i < path.length; i++) {
            Class<?> aClass1 = UtilsReflection.typeOfFields(model, path, i);
            if (aClass1 != null && isCollectionClass(aClass1.getTypeName())) {
                main = ((Root<?>) (main)).join(path[i]);
            } else {
                main = main.get(path[i]);
            }
        }
        return main;
    }

    private void create(StringField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Predicate predicate = null;
        String[] split = field.getName().split("\\.");

        Path<Object> objectPath = generatePath(split, root);
        Path<String> stringPath = generatePath(split, root);
        Path<Number> numberPath = generatePath(split, root);

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
            case LIKE_P:
                if (stringPath.getJavaType().getName().equals("java.lang.String"))
                    predicate = cb.like(stringPath, field.getValue() + "%");
                break;
            case LIKE_L:
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
                break;
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
                || nameClass.equals("java.lang.Float")
                || nameClass.equals("int")
                || nameClass.equals("long")
                || nameClass.equals("byte")
                || nameClass.equals("float")
                || nameClass.equals("short")
                || nameClass.equals("double");
    }

    private boolean isCollectionClass(String nameClass) {

        return nameClass.equals("java.util.List")
                || nameClass.equals("java.util.ArrayList")
                || nameClass.equals("java.util.LinkedList")
                || nameClass.equals("java.util.Set")
                || nameClass.equals("java.util.HashSet")
                || nameClass.equals("java.util.LinkedHashSet")
                || nameClass.equals("java.util.EnumSet");
    }


    private Number convertStringToNumber(String value) throws ParseException {
        return NumberFormat.getInstance().parse(value.replace(".", ","));
    }
}

