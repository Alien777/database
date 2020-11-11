package pl.lasota.database.reflection;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.List;
import java.util.function.Function;


import static org.assertj.core.api.Assertions.assertThat;
import static pl.lasota.database.reflection.UtilsReflections.getAllFieldWithAnnotation;
import static pl.lasota.database.reflection.UtilsReflections.getAllFieldWithoutAnnotation;

public class UtilsReflectionsTest {

    @Test
    public void allExistingFieldInStructureClass() {
        List<FieldClass> allField = UtilsReflections.getAllField(Blog.class);
        Assertions.assertThat(allField).isNotNull()
                .hasSize(12)
                .flatExtracting((Function<FieldClass, String>) FieldClass::getPath)
                .contains("url",
                        "person",
                        "fieldParent",
                        "fieldSecondParent",
                        "schoolList",
                        "person.name",
                        "person.secondName",
                        "person.school",
                        "schoolList.name",
                        "schoolList.number",
                        "person.school.numberOfSchool",
                        "person.school.typeOfSchool");

    }

    @Test
    public void allFieldWithAnnotation() {
        List<FieldClass> allField = getAllFieldWithAnnotation(Blog.class, TestAnnotation.class);
        Assertions.assertThat(allField).isNotNull()
                .hasSize(5)
                .flatExtracting((Function<FieldClass, String>) FieldClass::getPath)
                .contains("url",
                        "fieldParent",
                        "fieldSecondParent",
                        "schoolList",
                        "person.name");
    }

    @Test
    public void fieldExistingInStructurePath() {
        List<FieldClass> allField = UtilsReflections.getStructureFieldByPath(Blog.class, "person.school.numberOfSchool");
        Assertions.assertThat(allField).isNotNull()
                .hasSize(3)
                .flatExtracting((Function<FieldClass, String>) FieldClass::getPath)
                .contains("person",
                        "person.school",
                        "person.school.numberOfSchool").element(1)
                .isEqualTo("person.school");
    }

    @Test
    public void allFieldWithoutAnnotation() {
        List<FieldClass> allField = getAllFieldWithoutAnnotation(Blog.class, TestAnnotation.class);
        Assertions.assertThat(allField).isNotNull()
                .hasSize(7)
                .flatExtracting((Function<FieldClass, String>) FieldClass::getPath)
                .contains("person",
                        "person.secondName",
                        "person.school",
                        "schoolList.name",
                        "schoolList.number",
                        "person.school.typeOfSchool",
                        "person.school.numberOfSchool");
    }
}