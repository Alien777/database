package pl.lasota.tool.sr.reflection;

import org.junit.Test;

import java.util.List;
import java.util.function.Function;


import static org.assertj.core.api.Assertions.assertThat;
import static pl.lasota.tool.sr.reflection.UtilsReflections.getAllFieldWithAnnotation;
import static pl.lasota.tool.sr.reflection.UtilsReflections.getAllFieldWithoutAnnotation;

public class UtilsReflectionsTest {

    @Test
    public void allExistingFieldInStructureClass() {
        List<FieldClass> allField = UtilsReflections.getAllField(Blog.class);
        assertThat(allField).isNotNull()
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
                        "schoolList.nazwaszkoly",
                        "schoolList.adres",
                        "person.school.numberOfSchool",
                        "person.school.tyopeOfSchool");

    }

    @Test
    public void allFieldWithAnnotation() {
        List<FieldClass> allField = getAllFieldWithAnnotation(Blog.class, TestAnnotation.class);
        assertThat(allField).isNotNull()
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
        assertThat(allField).isNotNull()
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
        assertThat(allField).isNotNull()
                .hasSize(7)
                .flatExtracting((Function<FieldClass, String>) FieldClass::getPath)
                .contains("person",
                        "person.secondName",
                        "person.school",
                        "schoolList.nazwaszkoly",
                        "schoolList.adres",
                        "person.school.tyopeOfSchool",
                        "person.school.numberOfSchool");
    }
}