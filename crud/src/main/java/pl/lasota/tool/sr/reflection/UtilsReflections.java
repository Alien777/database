package pl.lasota.tool.sr.reflection;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class UtilsReflections {

    private UtilsReflections() {
    }

    public static List<FieldClass> getStructureFieldByPath(Class<?> baseClass, String path) {
        String[] partPaths = path.split("\\.");
        List<FieldClass> classes = new LinkedList<>();

        List<ReflectionField> reflectionFields = allField(baseClass);
        String rootPath = partPaths[0];
        for (int i = 0; i < partPaths.length; i++) {
            for (ReflectionField r : reflectionFields) {
                if (r.getPath().equals(rootPath)) {
                    classes.add(new FieldClass(r.getField().getName(), r.getPath(), r.getField(), r.getField().getType(), r.getParentField()));
                }
            }
            if (i + 1 < partPaths.length) {
                rootPath = rootPath + "." + partPaths[i + 1];
            }
        }
        return classes;
    }


    @SafeVarargs
    public static List<FieldClass> getAllFieldWithoutAnnotation(Class<?> baseClass, Class<? extends Annotation>... annotations) {
        return allField(baseClass)
                .stream()
                .filter(reflectionField -> Arrays.stream(annotations).noneMatch(c -> reflectionField.getField().isAnnotationPresent(c)))
                .map(r -> new FieldClass(r.getField().getName(), r.getPath(), r.getField(), r.getField().getType(), r.getParentField()))
                .collect(Collectors.toList());
    }

    @SafeVarargs
    public static List<FieldClass> getAllFieldWithAnnotation(Class<?> baseClass, Class<? extends Annotation>... annotations) {
        return allField(baseClass)
                .stream()
                .filter(reflectionField -> Arrays.stream(annotations).anyMatch(c -> reflectionField.getField().isAnnotationPresent(c)))
                .map(r -> new FieldClass(r.getField().getName(), r.getPath(), r.getField(), r.getField().getType(), r.getParentField()))
                .collect(Collectors.toList());
    }

    public static List<FieldClass> getAllField(Class<?> baseClass) {
        return allField(baseClass)
                .stream()
                .map(r -> new FieldClass(r.getField().getName(), r.getPath(), r.getField(), r.getField().getType(), r.getParentField()))
                .collect(Collectors.toList());
    }

    private static List<ReflectionField> allField(Class<?> baseClass) {
        LinkedList<ReflectionField> containerField = FieldUtils
                .getAllFieldsList(baseClass)
                .stream()
                .map(field -> {
                    ReflectionField reflectionField = new ReflectionField(field.getName(), field, baseClass);
                    reflectionField.setProjectClass(true);
                    return reflectionField;
                })
                .collect(Collectors.toCollection(LinkedList::new));

        while (true) {
            Optional<ReflectionField> reflectionFieldOptional = findNotCovered(containerField);
            if (reflectionFieldOptional.isEmpty()) {
                break;
            }

            ReflectionField reflectionField = reflectionFieldOptional.get();
            reflectionField.setCovered(true);
            LinkedList<ReflectionField> collect = new LinkedList<>();

            Field listMap = reflectionField.getField();
            if (!listMap.isAnnotationPresent(IgnoreMap.class)) {
                Type genericFieldType = listMap.getGenericType();
                if (genericFieldType instanceof ParameterizedType) {
                    ParameterizedType aType = (ParameterizedType) genericFieldType;
                    Type[] fieldArgTypes = aType.getActualTypeArguments();
                    for (Type fieldArgType : fieldArgTypes) {
                        Class<?> stringListClass = (Class<?>) aType.getActualTypeArguments()[0];
                        collect.addAll(FieldUtils.getAllFieldsList((Class<?>) fieldArgType).stream()
                                .map(field -> new ReflectionField(reflectionField.getPath() + "." + field.getName(), field, stringListClass))
                                .collect(Collectors.toCollection(LinkedList::new)));
                    }
                }
            }
            if (!reflectionField.getField().isAnnotationPresent(IgnoreMap.class)) {
                collect.addAll(FieldUtils.getAllFieldsList(reflectionField.getField()
                        .getType()).stream()
                        .map(field -> new ReflectionField(reflectionField.getPath() + "." + field.getName(), field, reflectionField.getField().getType()))
                        .collect(Collectors.toCollection(LinkedList::new)));
            }

            collect.forEach(UtilsReflections::markerProjectClass);

            containerField.addAll(collect);
        }

        return containerField.stream().filter(r -> r.isCovered() && r.isProjectClass()).collect(Collectors.toList());
    }

    private static void markerProjectClass(ReflectionField reflectionFields) {
        String[] s1 = reflectionFields.getField().toString().split(" ");
        String s = s1[s1.length - 1];

        if (!s.contains("java.lang") && !s.contains("java.util") && !s.contains("java.time")) {
            reflectionFields.setProjectClass(true);
        }

        if (reflectionFields.getField().getType().isEnum()) {
            reflectionFields.setProjectClass(true);
            reflectionFields.setCovered(true);
        }
    }

    private static Optional<ReflectionField> findNotCovered(List<ReflectionField> reflectionFields) {
        return reflectionFields.stream().filter(r -> !r.isCovered() && r.isProjectClass()).findFirst();
    }
}
