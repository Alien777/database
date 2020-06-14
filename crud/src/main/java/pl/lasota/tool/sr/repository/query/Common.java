package pl.lasota.tool.sr.repository.query;

import pl.lasota.tool.sr.reflection.FieldClass;
import pl.lasota.tool.sr.reflection.UtilsReflections;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Pattern;

public class Common {

    public <T> Path<T> generatePath(final String path, final Root<?> root, Class<?> aClass) {
        Path<T> main;
        List<FieldClass> paths = UtilsReflections.getStructureFieldByPath(aClass, path);
        FieldClass fieldClass = paths.get(0);

        if (fieldClass.getTypeField() != null && isCollectionClass(fieldClass.getTypeField().getTypeName())) {
            main = root.join(fieldClass.getName());
        } else {
            main = root.get(fieldClass.getName());
        }

        for (int i = 1; i < paths.size(); i++) {
            FieldClass fieldClass1 = paths.get(i);
            if (fieldClass1.getTypeField() != null && isCollectionClass(fieldClass1.getTypeField().getTypeName())) {
                main = ((Root<?>) (main)).join(fieldClass1.getName(), JoinType.RIGHT);
            } else {
                main = main.get(fieldClass1.getName());
            }
        }
        return main;
    }

    public boolean isCollectionClass(String nameClass) {
        return nameClass.equals("java.util.List")
                || nameClass.equals("java.util.ArrayList")
                || nameClass.equals("java.util.LinkedList")
                || nameClass.equals("java.util.Set")
                || nameClass.equals("java.util.HashSet")
                || nameClass.equals("java.util.LinkedHashSet")
                || nameClass.equals("java.util.EnumSet");
    }
}
