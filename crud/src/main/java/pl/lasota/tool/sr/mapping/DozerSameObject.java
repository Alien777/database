package pl.lasota.tool.sr.mapping;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.*;
import com.google.common.collect.*;
import org.springframework.security.core.parameters.P;
import pl.lasota.tool.sr.reflection.FieldClass;
import pl.lasota.tool.sr.reflection.UtilsReflections;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * * Simple Implementation using Dozer Mapper to map between two different object or the same object
 */
public final class DozerSameObject<S_D> {

    private final Mapper mapper;


    public DozerSameObject(Class<S_D> sourceClass) {
        List<BeanMappingBuilder> configs = new LinkedList<>();

        List<FieldClass> copyByReferences = UtilsReflections.getAllFieldWithAnnotation(sourceClass, CopyByReference.class);
        List<FieldClass> notUpdate = UtilsReflections.getAllFieldWithAnnotation(sourceClass, NotUpdating.class);

        HashMap<Class<?>, List<FieldClass>> field = new HashMap<>();

        Consumer<FieldClass> consumer = f -> {
            List<FieldClass> fieldClasses = field.get(f.getParentClass());
            if (fieldClasses == null) {
                LinkedList<FieldClass> objects = new LinkedList<>();
                objects.add(f);
                field.put(f.getParentClass(), objects);
            } else {
                fieldClasses.add(f);
            }
        };

        copyByReferences.forEach(consumer);
        notUpdate.forEach(consumer);

        configs.add(new BeanMappingBuilder() {
            protected void configure() {
                field.forEach((aClass, fieldClasses) -> {
                    TypeMappingBuilder mapping = mapping(aClass, aClass, TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(true));
                    fieldClasses.forEach(fieldClass ->
                    {
                        if (fieldClass.getField().isAnnotationPresent(NotUpdating.class)) {
                            mapping.exclude(fieldClass.getPath());
                        } else if (fieldClass.getField().isAnnotationPresent(CopyByReference.class)) {
                            mapping.fields(fieldClass.getPath(), fieldClass.getPath(), FieldsMappingOptions.copyByReference());
                        }
                    });
                });
            }
        });

        field.forEach((aClass, fieldClasses) -> {
            System.out.println(aClass);
            fieldClasses.forEach(System.out::println);
        });

        mapper = DozerBeanMapperBuilder.create().withMappingBuilders(configs).build();

    }


    public void mapper(S_D source, S_D destination) {
        mapper.map(source, destination);
    }

}

