package pl.lasota.tool.crud.mapping;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * * Simple Implementation using Dozer Mapper to map between two different object or the same object
 *
 * @param <SOURCE>
 * @param <DESTINATION>
 */
public final class DozerSameObject<SOURCE, DESTINATION> {

    private final DozerBeanMapper mapper = new DozerBeanMapper();

    public DozerSameObject(Class<SOURCE> sourceClass, Class<DESTINATION> destinationClass) {

        Set<String> name = new HashSet<>();
        LinkedList<Field> fields = new LinkedList<>();
        List<Field> fields1 = Arrays.asList(sourceClass.getDeclaredFields());
        fields.addAll(fields1);

        while (!fields.isEmpty()) {
            Field poll = fields.poll();
            System.out.println(poll.getName());
            Class<?> type = poll.getType();

            fields.addAll(Arrays.asList(type.getDeclaredFields()));
            if (poll.isAnnotationPresent(CopyByReference.class)) {
                name.add(type.getCanonicalName()+"."+poll.getName());
            }
        }

        name.forEach(s -> System.out.println(s));
        mapper.addMapping(new BeanMappingBuilder() {
            protected void configure() {
                mapping(sourceClass, destinationClass, TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(true));
            }
        });

    }

    public void mapper(SOURCE source, DESTINATION destination) {


        mapper.map(source, destination);
    }
}
