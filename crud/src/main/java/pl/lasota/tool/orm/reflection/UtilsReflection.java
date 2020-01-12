package pl.lasota.tool.orm.reflection;

import org.hibernate.loader.plan.build.internal.returns.CollectionFetchableElementCompositeGraph;
import org.springframework.data.util.Pair;
import org.springframework.data.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class UtilsReflection {

    public static void findAllFieldsContains(Class<?> baseClass, Class<? extends Annotation> annotationClass,
                                             Consumer<FieldNode> mapper) {

        Queue<Field> fields = new ArrayDeque<>(Arrays.asList(baseClass.getDeclaredFields()));
        HashSet<FieldNode> rawStructure = fields.stream().map(f ->
                new FieldNode(null, f)).collect(Collectors.toCollection(HashSet::new));

        while (!fields.isEmpty()) {
            Field currentField = fields.poll();
            Field[] childFields = currentField.getType().getDeclaredFields();

            Optional<FieldNode> findNode = rawStructure.stream().filter(n -> n.getField().equals(currentField)).findFirst();

            final List<Field> childFieldsList = Arrays.asList(childFields);
            if (findNode.isPresent()) {
                FieldNode existingNode = findNode.get();
                if (existingNode.getField().isAnnotationPresent(annotationClass)) {
                    existingNode.setContains(true);
                }
                childFieldsList.forEach(field -> rawStructure.add(new FieldNode(existingNode, field)));

            }
            fields.addAll(childFieldsList);
        }
        rawStructure.stream().filter(FieldNode::isContains).forEach(mapper);

    }

    public static Class<?> typeOfFields(Class<?> baseField, String path) {
        String[] paths = path.split("\\.");
        return typeOfFields(baseField, paths, paths.length);
    }

    public static Class<?> typeOfFields(Class<?> baseField, String[] path, int level) {
        List<Class<?>> objects = new ArrayList<>();
        objects.add(baseField);
        return typeOfFields(objects, path, level);
    }

    public static Class<?> typeOfFields(List<Class<?>> baseFields, String[] path, int level) {

        Field main = null;
        int index = 0;
        Queue<Field> fields = new ArrayDeque<>();
        for (Class<?> baseField : baseFields) {
            fields.addAll(Arrays.asList(baseField.getDeclaredFields()));
        }

        for (String s : path) {
            index++;
            while (!fields.isEmpty()) {
                Field currentField = fields.poll();
                if (currentField.getName().equals(s)) {
                    main = currentField;
                    break;
                }

            }
            if (main == null) {
                break;
            }

            if (level == index) {
                break;
            }

            fields = new ArrayDeque<>();

            if (main.getGenericType() instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) main.getGenericType();
                if (type.getActualTypeArguments() != null && type.getActualTypeArguments().length > 0) {
                    Class<?> stringListClass = (Class<?>) type.getActualTypeArguments()[0];
                    fields.addAll(Arrays.asList(stringListClass.getDeclaredFields()));
                }
            }
            fields.addAll(Arrays.asList(main.getType().getDeclaredFields()));
            fields.addAll(findAllFieldFromSuper(main));

            main = null;

        }

        if (main != null) {
            return main.getType();
        } else {
            return null;
        }
    }

    private static List<Field> findAllFieldFromSuper(Field root) {
        List<Field> fields = new LinkedList<>();

        Class<?> superclass = root.getType().getSuperclass();

        while (superclass != null) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));

            superclass = superclass.getSuperclass();
        }
        return fields;
    }

    public static String getPath(FieldNode fieldNodes) {

        StringBuilder sb = new StringBuilder();
        FieldNode temp = fieldNodes;
        while (temp != null) {
            sb.insert(0, temp.getField().getName());
            if (temp.getNext() != null) {
                sb.insert(0, ".");
            }
            temp = temp.getNext();
        }
        return sb.toString();
    }

    public static Pair<String, Field> getPathWithFile(FieldNode fieldNodes) {

        StringBuilder sb = new StringBuilder();
        FieldNode temp = fieldNodes;
        while (temp != null) {
            sb.insert(0, temp.getField().getName());
            if (temp.getNext() != null) {
                sb.insert(0, ".");
            }
            temp = temp.getNext();
        }
        return Pair.of(sb.toString(), fieldNodes.getField());
    }

}
