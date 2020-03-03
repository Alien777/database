package pl.lasota.tool.sr.repository;


import org.springframework.data.util.Pair;
import pl.lasota.tool.sr.field.Sort;
import pl.lasota.tool.sr.field.*;
import pl.lasota.tool.sr.field.definition.*;
import pl.lasota.tool.sr.reflection.FieldClass;
import pl.lasota.tool.sr.reflection.UtilsReflections;

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


        Path<Object> objectPath = generatePath(field.getName(), root);

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

        if (field instanceof RangeField) {
            try {
                create((RangeField) field, predicates, root, cb);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (field instanceof SimpleField) {
            try {
                Predicate predicate = create((SimpleField) field, root, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (field instanceof AllOneEqualOne) {
            try {
                Predicate predicate = create((AllOneEqualOne) field, cb);
                if (predicate != null) {
                    predicates.add(predicate);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else if (field instanceof MultipleValuesField) {
            try {
                create((MultipleValuesField) field, predicates, root, cb);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return predicates;
    }


    private void create(MultipleValuesField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {

        List<Predicate> predOr = new LinkedList<>();
        for (String value : field.getValue()) {
            Predicate predicate = create(
                    new SimpleField(field.getName(), value, field.getSelector(), field.condition()),
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
        Path<Object> objectPath = generatePath(field.getName(), root);

        Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
        return Pair.of(objectPath, o);
    }

    private void create(RangeField field, List<Predicate> predicates, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Predicate predicate = null;

        Path objectPath = generatePath(field.getName(), root);

        if (field.condition() == Operator.RANGE) {
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

    private Expression<String> normalized(Path<String> path, CriteriaField<?> criteriaField, CriteriaBuilder criteriaBuilder) {

        List<Normalizer> normalizers = criteriaField.normalizedValue();
        if (normalizers == null || normalizers.size() == 0) {
            return path;
        }
        Expression<String> lower = path;
        for (int i = 0; i < normalizers.size(); i++) {

            Normalizer normalizer = normalizers.get(i);
            if (Normalizer.LOWER_CASE == normalizer) {
                lower = criteriaBuilder.lower(lower);
            } else {
                lower = criteriaBuilder.lower(lower);
            }
            if (Normalizer.ASCII == normalizer) {
                lower = criteriaBuilder.function("unaccent", String.class, lower);
            }
        }

        return lower;
    }


    private <T> Path<T> generatePath(final String path, final Root<MODEL> root) {
        Path<T> main;
        List<FieldClass> paths = UtilsReflections.getStructureFieldByPath(model, path);
        FieldClass fieldClass = paths.get(0);

        if (fieldClass.getTypeField() != null && isCollectionClass(fieldClass.getTypeField().getTypeName())) {
            main = root.join(fieldClass.getName());
        } else {
            main = root.get(fieldClass.getName());
        }

        for (int i = 1; i < paths.size(); i++) {
            FieldClass fieldClass1 = paths.get(i);
            if (fieldClass1.getTypeField() != null && isCollectionClass(fieldClass1.getTypeField().getTypeName())) {
                main = ((Root<?>) (main)).join(fieldClass1.getName());
            } else {
                main = main.get(fieldClass1.getName());
            }
        }
        return main;
    }

    private Predicate create(AllOneEqualOne field, CriteriaBuilder cb) throws ParseException {

        if (field.condition() == Operator.GET_ALL) {
            return cb.equal(cb.literal(1), 1);
        }
        return null;
    }

    private Predicate create(SimpleField field, Root<MODEL> root, CriteriaBuilder cb) throws ParseException {
        Predicate predicate = null;


        Path objectPath = generatePath(field.getName(), root);

        switch (field.condition()) {
            case GET_ALL:
                predicate = cb.equal(cb.literal(1), 1);
                break;
            case EQUALS:
                Object o = convertClass(objectPath.getJavaType().getTypeName(), field.getValue());
                if (o != null) {
                    predicate = cb.equal(normalized(objectPath, field, cb), o);
                }
                break;
            case LIKE:
                if (objectPath.getJavaType().getName().equals(JAVA_LANG_STRING))
                    predicate = cb.like(normalized(objectPath, field, cb), "%" + field.getValue() + "%");
                break;
            case LIKE_P:
                if (objectPath.getJavaType().getName().equals(JAVA_LANG_STRING))
                    predicate = cb.like(normalized(objectPath, field, cb), field.getValue() + "%");
                break;
            case LIKE_L:
                if (objectPath.getJavaType().getName().equals(JAVA_LANG_STRING))
                    predicate = cb.like(normalized(objectPath, field, cb), "%" + field.getValue());
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

