package pl.lasota.tool.orm.repository;


import org.springframework.data.util.Pair;
import pl.lasota.tool.orm.common.Sort;
import pl.lasota.tool.orm.field.*;
import pl.lasota.tool.orm.reflection.UtilsReflection;
import pl.lasota.tool.orm.field.SetField;

import javax.persistence.criteria.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public final class CriteriaFieldMapping<MODEL> {

    private final static Pattern NUMBER_PATTERN = Pattern.compile("-?\\d+([.|,]\\d+)?");
    private static final String DOT_REGEX_SPLIT = "\\.";
    private static final String JAVA_LANG_STRING = "java.lang.String";
    private final Class<MODEL> model;

    public CriteriaFieldMapping(Class<MODEL> model) {
        this.model = model;
    }

    public Order map(SortField field, Root<MODEL> root, CriteriaBuilder cb) {

        String[] split = field.getName().split("\\.");
        Path<Object> objectPath = generatePath(split, root);

        Order order;
        if (field.getValue() == Sort.ASC) {
            order = cb.asc(objectPath);
        } else {
            order = cb.desc(objectPath);
        }
        return order;
    }

    public Pair<Path<Object>, Object> map(SetField field, Root<MODEL> modelRoot) {
        try {
            return create(field, modelRoot);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Predicate> map(CriteriaField<?> field, Root<MODEL> root, CriteriaBuilder cb) {

        List<Predicate> predicates = new LinkedList<>();

        if (field instanceof RangeStringField) {
            try {
                create((RangeStringField) field, predicates, root, cb);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (field instanceof StringField) {
            try {
                Predicate predicate = create((StringField) field, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (field instanceof StringFields) {
            try {
                create((StringFields) field, predicates, root, cb);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return predicates;
    }

    private void create(StringFields field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {

        List<Predicate> predOr = new LinkedList<>();
        for (String value : field.getValue()) {
            Predicate predicate = create(
                    new StringField(field.getName(), value, field.getSelector(), field.condition()),
                    root, cb);
            if (predicate != null) {
                predOr.add(predicate);
            }
        }
        Predicate[] toArray = predOr.toArray(new Predicate[0]);
        if (toArray.length >= 1)
            predicates.add(cb.or(toArray));
    }

    private Pair<Path<Object>, Object> create(SetField field, Root<MODEL> root) throws ParseException {

        String[] split = field.getName().split(DOT_REGEX_SPLIT);
        Path<Object> objectPath = generatePath(split, root);

        Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
        return Pair.of(objectPath, o);
    }

    private void create(RangeStringField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Predicate predicate = null;

        String[] split = field.getName().split("\\.");
        Path objectPath = generatePath(split, root);

        if (field.condition() == Operator.BETWEEN) {
            Range<String> range = field.getValue();
            if (!isNumeric(range.getMinimum()) && !isNumeric(range.getMaximum())) {
                return;
            }

            Number first = convertStringToNumber(range.getMinimum());
            Number second = convertStringToNumber(range.getMaximum());
            if (isNumeric(range.getMinimum()) && isNumeric(range.getMaximum())) {
                if (objectPath.getJavaType().getName().equals("java.lang.Double")
                        || objectPath.getJavaType().getName().equals("double")) {
                    predicate = cb.between(
                            objectPath,
                            first.doubleValue(),
                            second.doubleValue());
                }
                if (objectPath.getJavaType().getName().equals("java.lang.Integer")
                        || objectPath.getJavaType().getName().equals("int")) {
                    predicate = cb.between(
                            objectPath,
                            first.intValue(),
                            second.intValue());
                }
                if (objectPath.getJavaType().getName().equals("java.lang.Long")
                        || objectPath.getJavaType().getName().equals("long")) {
                    predicate = cb.between(
                            objectPath,
                            first.longValue(),
                            second.longValue());
                }
                if (objectPath.getJavaType().getName().equals("java.lang.Short") ||
                        objectPath.getJavaType().getName().equals("short")) {
                    predicate = cb.between(
                            objectPath,
                            first.shortValue(),
                            second.shortValue());
                }
                if (objectPath.getJavaType().getName().equals("java.lang.Float") ||
                        objectPath.getJavaType().getName().equals("float")) {
                    predicate = cb.between(
                            objectPath,
                            first.floatValue(),
                            second.floatValue());
                }
            }
        }

        if (predicate != null) {
            predicates.add(predicate);
        }
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

    private Predicate create(StringField field, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Predicate predicate = null;
        String[] split = field.getName().split("\\.");

        Path objectPath = generatePath(split, root);

        switch (field.condition()) {
            case EQUALS:
                Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
                if (o != null) {
                    predicate = cb.equal(objectPath, o);
                }
                break;
            case LIKE:
                if (objectPath.getJavaType().getName().equals(JAVA_LANG_STRING))
                    predicate = cb.like(objectPath, "%" + field.getValue() + "%");
                break;
            case LIKE_P:
                if (objectPath.getJavaType().getName().equals(JAVA_LANG_STRING))
                    predicate = cb.like(objectPath, field.getValue() + "%");
                break;
            case LIKE_L:
                if (objectPath.getJavaType().getName().equals(JAVA_LANG_STRING))
                    predicate = cb.like(objectPath, "%" + field.getValue());
                break;
            case GE:
                if (isNumeric(field.getValue()) && isFromNumberClass(objectPath.getJavaType().getName()))
                    predicate = cb.ge(objectPath, convertStringToNumber(field.getValue()));
                break;
            case GT:
                if (isNumeric(field.getValue()) && isFromNumberClass(objectPath.getJavaType().getName()))
                    predicate = cb.gt(objectPath, convertStringToNumber(field.getValue()));
                break;
            case LE:
                if (isNumeric(field.getValue()) && isFromNumberClass(objectPath.getJavaType().getName()))
                    predicate = cb.le(objectPath, convertStringToNumber(field.getValue()));
                break;
            case LT:
                if (isNumeric(field.getValue()) && isFromNumberClass(objectPath.getJavaType().getName()))
                    predicate = cb.lt(objectPath, convertStringToNumber(field.getValue()));
                break;
        }

        return predicate;
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

