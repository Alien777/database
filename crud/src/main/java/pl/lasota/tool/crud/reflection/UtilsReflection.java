package pl.lasota.tool.crud.reflection;

import org.springframework.data.util.Pair;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class UtilsReflection {

    public static void findAllFieldsContains(Class<?> baseClass, Class<? extends Annotation> annotationClass,
                                             Consumer<FieldNode> mapper) {
        HashSet<FieldNode> rawStructure = new HashSet<>();
        Queue<Field> fields = new ArrayDeque<>();

        fields.addAll(Arrays.asList(baseClass.getDeclaredFields()));
        rawStructure.addAll(fields.stream().map(f -> new FieldNode(null, f)).collect(Collectors.toList()));

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
