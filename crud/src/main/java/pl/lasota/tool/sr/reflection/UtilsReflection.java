package pl.lasota.tool.sr.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class UtilsReflection {

    public static void findAllFieldsContains(Class<?> baseClass, Class<? extends Annotation> annotationClass,
                                             Consumer<FieldNode> mapper) {

        Queue<Field> fields = new ArrayDeque<>(Arrays.asList(baseClass.getDeclaredFields()));

        if (baseClass.getSuperclass() != null) {
            List<Field> fields1 = Arrays.asList(baseClass.getSuperclass().getDeclaredFields());
            fields.addAll(fields1);
        }

        HashSet<FieldNode> rawStructure = fields.stream().map(f ->
                new FieldNode(null, f)).collect(Collectors.toCollection(HashSet::new));

        while (!fields.isEmpty()) {
            Field currentField = fields.poll();
            Field[] childFields = new Field[0];
            if (!currentField.toString().contains("java.lang")
                    && !currentField.toString().contains("java.util")
                    && currentField.getClass().isEnum()) {
                childFields = currentField.getType().getDeclaredFields();
            }

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


    public static Class<?> typeOfFields(Class<?> baseField, String[] path, int level) {

        Field main = null;
        Queue<Field> fields = new ArrayDeque<>();
        fields.addAll(Arrays.asList(baseField.getDeclaredFields()));
        fields.addAll(findAllFieldFromSuper(baseField));
        for (int j = 0; j <= level; j++) {

            String fieldName = path[j];
            while (!fields.isEmpty()) {
                Field currentField = fields.poll();
                if (currentField.getName().equals(fieldName)) {
                    main = currentField;
                    break;
                }
            }

            if (main == null) {
                break;
            }
            if (j == level) {
                break;
            }
            fields = new ArrayDeque<>();
            fields.addAll(Arrays.asList(main.getType().getDeclaredFields()));
            fields.addAll(findAllFieldFromSuper(main.getType()));
            Class<?> aClass = genericClass(main);
            if (aClass != null) {
                fields.addAll(Arrays.asList(aClass.getDeclaredFields()));
                fields.addAll(findAllFieldFromSuper(aClass));
            }

        }
        if (main != null) {
            return main.getType();
        } else {
            return null;
        }
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


    private static List<Field> findAllFieldFromSuper(Class<?> root) {
        List<Field> fields = new LinkedList<>();
        Class<?> superclass = root.getSuperclass();
        while (superclass != null) {
            fields.addAll(Arrays.asList(superclass.getDeclaredFields()));
            superclass = superclass.getSuperclass();
        }
        return fields;
    }

    private static Class<?> genericClass(Field root) {
        if (root.getGenericType() instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) root.getGenericType();
            if (type.getActualTypeArguments() != null && type.getActualTypeArguments().length > 0) {
                return (Class<?>) type.getActualTypeArguments()[0];
            }
        }
        return null;
    }

}
