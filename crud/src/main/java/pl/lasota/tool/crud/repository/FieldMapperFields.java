package pl.lasota.tool.crud.repository;


import pl.lasota.tool.crud.repository.annotaction.AliasColumnDiscovery;
import pl.lasota.tool.crud.repository.field.*;

import javax.persistence.criteria.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public final class FieldMapperFields<MODEL> implements MapperFields<MODEL> {

    private final static Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+([.|,]\\d+)?");
    private final AliasColumnDiscovery<MODEL> aliasColumnDiscovery;

    public FieldMapperFields() {
        aliasColumnDiscovery = new AliasColumnDiscovery<>();
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
        Class<MODEL> bindableJavaType = root.getModel().getBindableJavaType();
        Order order = null;
        if (field.getValue() == Sort.ASC) {
            order = cb.asc(root.get(mapAlias(field.getName(), bindableJavaType)));
        }
        if (field.getValue() == Sort.DESC) {
            order = cb.desc(root.get(mapAlias(field.getName(), bindableJavaType)));
        }

        if (order != null) {
            orders.add(order);
        }
    }

    @Override
    public void map(SetField field, Map<String, Object> criteriaUpdate, Root<MODEL> modelRoot) {
        try {
            create(field, criteriaUpdate, modelRoot);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void create(SetField field, Map<String, Object> criteriaUpdate, Root<MODEL> root) throws ParseException {
        Class<MODEL> bindableJavaType = root.getModel().getBindableJavaType();
        String s = mapAlias(field.getName(), bindableJavaType);
        Path<Object> objectPath = root.get(s);
        Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
        criteriaUpdate.put(s, o);
    }

    private void create(RangeStringField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Class<MODEL> bindableJavaType = root.getModel().getBindableJavaType();
        Predicate predicate = null;
        Path<Object> path = root.get(mapAlias(field.getName(), bindableJavaType));
        String objectPath = path.getJavaType().getName();

        if (field.condition() == Condition.BETWEEN) {
            Range<String> range = field.getValue();
            if (!isNumeric(range.getMinimum()) && !isNumeric(range.getMaximum())) {
                return;
            }

            Number first = convertStringToNumber(range.getMinimum());
            Number second = convertStringToNumber(range.getMaximum());
            if (isNumeric(range.getMinimum()) && isNumeric(range.getMaximum())) {
                if (objectPath.equals("java.lang.Double")) {
                    predicate = cb.between(
                            root.get(mapAlias(field.getName(), bindableJavaType)),
                            first.doubleValue(),
                            second.doubleValue());
                }
                if (objectPath.equals("java.lang.Integer")) {
                    predicate = cb.between(
                            root.get(mapAlias(field.getName(), bindableJavaType)),
                            first.intValue(),
                            second.intValue());
                }
                if (objectPath.equals("java.lang.Long")) {
                    predicate = cb.between(
                            root.get(mapAlias(field.getName(), bindableJavaType)),
                            first.longValue(),
                            second.longValue());
                }
                if (objectPath.equals("java.lang.Short")) {
                    predicate = cb.between(
                            root.get(mapAlias(field.getName(), bindableJavaType)),
                            first.shortValue(),
                            second.shortValue());
                }
                if (objectPath.equals("java.lang.Float")) {
                    predicate = cb.between(
                            root.get(mapAlias(field.getName(), bindableJavaType)),
                            first.floatValue(),
                            second.floatValue());
                }
            }
        }

        if (predicate != null) {
            predicates.add(predicate);
        }
    }

    private String mapAlias(String name, Class<MODEL> bindableJavaType) {
        return aliasColumnDiscovery.discover(name, bindableJavaType);
    }


    private void create(StringField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {

        Class<MODEL> bindableJavaType = root.getModel().getBindableJavaType();
        Predicate predicate = null;
        Path<Object> objectPath = root.get(mapAlias(field.getName(), bindableJavaType));

        switch (field.condition()) {
            case EQUALS:
                Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
                if (o != null) {
                    predicate = cb.equal(objectPath, o);
                }
                break;
            case LIKE:
                if (objectPath.getJavaType().getName().equals("java.lang.String"))
                    predicate = cb.like(root.get(mapAlias(field.getName(), bindableJavaType)), "%" + field.getValue() + "%");

                break;
            case LIKE_L:
                if (objectPath.getJavaType().getName().equals("java.lang.String"))
                    predicate = cb.like(root.get(mapAlias(field.getName(), bindableJavaType)), field.getValue() + "%");

                break;
            case LIKE_P:
                if (objectPath.getJavaType().getName().equals("java.lang.String"))
                    predicate = cb.like(root.get(mapAlias(field.getName(), bindableJavaType)), "%" + field.getValue());
                break;
            case GE:
                if (isNumeric(field.getValue()) && isFromNumberClass(objectPath.getJavaType().getName()))
                    predicate = cb.ge(root.get(mapAlias(field.getName(), bindableJavaType)), convertStringToNumber(field.getValue()));
                break;
            case GT:
                if (isNumeric(field.getValue()) && isFromNumberClass(objectPath.getJavaType().getName()))
                    predicate = cb.gt(root.get(mapAlias(field.getName(), bindableJavaType)), convertStringToNumber(field.getValue()));
                break;
            case LE:
                if (isNumeric(field.getValue()) && isFromNumberClass(objectPath.getJavaType().getName()))
                    predicate = cb.le(root.get(mapAlias(field.getName(), bindableJavaType)), convertStringToNumber(field.getValue()));
                break;
            case LT:
                if (isNumeric(field.getValue()) && isFromNumberClass(objectPath.getJavaType().getName()))
                    predicate = cb.lt(root.get(mapAlias(field.getName(), bindableJavaType)), convertStringToNumber(field.getValue()));
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

