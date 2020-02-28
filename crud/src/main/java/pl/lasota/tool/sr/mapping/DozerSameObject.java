package pl.lasota.tool.sr.mapping;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.*;
import pl.lasota.tool.sr.reflection.FieldClass;
import pl.lasota.tool.sr.reflection.UtilsReflections;

import java.util.LinkedList;
import java.util.List;

/**
 * * Simple Implementation using Dozer Mapper to map between two different object or the same object
 */
public final class DozerSameObject<S_D> {

    private final Mapper mapper;


    public DozerSameObject(Class<S_D> sourceClass) {
        List<BeanMappingBuilder> configs = new LinkedList<>();

        List<FieldClass> copyByReferences = UtilsReflections.getAllFieldWithAnnotation(sourceClass, CopyByReference.class);
        List<FieldClass> notUpdate = UtilsReflections.getAllFieldWithAnnotation(sourceClass, NotUpdating.class);
        List<FieldClass> othersFields = UtilsReflections.getAllFieldWithoutAnnotation(sourceClass, NotUpdating.class, CopyByReference.class);

        configs.add(new BeanMappingBuilder() {
            protected void configure() {

                othersFields.forEach(fieldClass -> {
                    mapping(fieldClass.getParentClass(), fieldClass.getParentClass(), TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(true));
                });
                copyByReferences.forEach(fieldClass -> {
                    mapping(fieldClass.getParentClass(), fieldClass.getParentClass(), TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(true))
                            .fields(fieldClass.getPath(), fieldClass.getPath(), FieldsMappingOptions.copyByReference());
                });
                notUpdate.forEach(fieldClass -> {
                    mapping(fieldClass.getParentClass(), fieldClass.getParentClass(), TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(true))
                            .exclude(fieldClass.getPath());
                });
            }
        });


        mapper = DozerBeanMapperBuilder.create().withMappingBuilders(configs).build();

    }


    public void mapper(S_D source, S_D destination) {
        mapper.map(source, destination);
    }

}
