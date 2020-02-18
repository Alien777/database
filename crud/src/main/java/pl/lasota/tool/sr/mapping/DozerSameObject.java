package pl.lasota.tool.sr.mapping;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;
import com.github.dozermapper.core.loader.api.FieldsMappingOptions;
import com.github.dozermapper.core.loader.api.TypeMappingBuilder;
import com.github.dozermapper.core.loader.api.TypeMappingOptions;
import pl.lasota.tool.sr.reflection.UtilsReflection;

import java.util.LinkedList;
import java.util.List;

/**
 * * Simple Implementation using Dozer Mapper to map between two different object or the same object
 */
public final class DozerSameObject<S_D> {

    private final Mapper mapper;


    public DozerSameObject(Class<S_D> sourceClass) {
        List<String> copyByrReferences = new LinkedList<>();

        UtilsReflection.findAllFieldsContains(sourceClass, CopyByReference.class, fieldNode -> copyByrReferences.add(UtilsReflection.getPath(fieldNode)));

        List<BeanMappingBuilder> configs = new LinkedList<>();
        BeanMappingBuilder beanMappingBuilder = new BeanMappingBuilder() {
            protected void configure() {
                mapping(sourceClass, sourceClass, TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(true));
            }
        };
        configs.add(beanMappingBuilder);


        List<String> copyByReference = new LinkedList<>();
        UtilsReflection.findAllFieldsContains(sourceClass, CopyByReference.class, fieldNode -> copyByReference.add(UtilsReflection.getPath(fieldNode)));
        BeanMappingBuilder coptyConfig = new BeanMappingBuilder() {
            protected void configure() {
                TypeMappingBuilder mapping = mapping(sourceClass, sourceClass, TypeMappingOptions.mapNull(false),
                        TypeMappingOptions.mapEmptyString(true));
                copyByReference.forEach(s -> mapping.fields(s, s, FieldsMappingOptions.copyByReference()));
            }
        };
        configs.add(coptyConfig);


        List<String> notMapping = new LinkedList<>();
        UtilsReflection.findAllFieldsContains(sourceClass, NotUpdating.class, fieldNode -> notMapping.add(UtilsReflection.getPath(fieldNode)));
        BeanMappingBuilder reference = new BeanMappingBuilder() {
            protected void configure() {
                TypeMappingBuilder mapping = mapping(sourceClass, sourceClass, TypeMappingOptions.mapNull(false),
                        TypeMappingOptions.mapEmptyString(true));
                notMapping.forEach(mapping::exclude);
            }
        };
        configs.add(reference);
        mapper = DozerBeanMapperBuilder.create().withMappingBuilders(configs).build();
    }


    public void mapper(S_D source, S_D destination) {
        mapper.map(source, destination);
    }
}
