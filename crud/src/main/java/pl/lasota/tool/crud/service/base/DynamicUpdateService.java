package pl.lasota.tool.crud.service.base;


import org.springframework.data.util.Pair;
import org.springframework.transaction.annotation.Transactional;
import pl.lasota.tool.crud.common.Condition;
import pl.lasota.tool.crud.common.CriteriaType;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.reflection.UtilsReflection;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.repository.field.CriteriaField;
import pl.lasota.tool.crud.repository.field.SetField;
import pl.lasota.tool.crud.repository.mapping.FieldMapping;
import pl.lasota.tool.crud.repository.update.SimpleUpdateRepository;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.repository.update.criteria.UpdateCriteriaSpecification;
import pl.lasota.tool.crud.service.SpecificationProvider;
import pl.lasota.tool.crud.service.UpdateService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.OneToOne;
import java.util.*;
import java.util.stream.Collectors;

public class DynamicUpdateService<MODEL extends EntityBase> implements UpdateService, SpecificationProvider<Specification> {

    private final FieldMapping<MODEL> map;

    private Map<String, UpdateRepository<?>> entities = new TreeMap<>();

    public DynamicUpdateService(EntityManager emf, Class<MODEL> modelClass) {

        map = new FieldMapping<>(modelClass);

        UtilsReflection.findAllFieldsContains(modelClass, OneToOne.class, fieldNode -> {
            Pair<String, java.lang.reflect.Field> pathWithFile = UtilsReflection.getPathWithFile(fieldNode);
            String path = pathWithFile.getFirst();
            Class type = pathWithFile.getSecond().getType();
            SimpleUpdateRepository<? extends EntityBase> repo = new SimpleUpdateRepository<>(emf);
            repo.modelClass(type);
            entities.put(path, repo);
        });
        entities.forEach((a, s) -> System.out.println("TEST " + a + " " + s));

    }

    @Override
    @Transactional
    public List<Long> update(List<Field<?>> source) {
        Set<Long> updated = new HashSet<>();
        List<CriteriaField<?>> filter = filter(source).stream().filter(criteriaField -> criteriaField.getCriteriaType() == CriteriaType.SET).collect(Collectors.toList());
        List<CriteriaField<?>> criteria = filter(source).stream().filter(criteriaField -> criteriaField.getCriteriaType() != CriteriaType.SET).collect(Collectors.toList());
        Map<UpdateRepository<?>, List<CriteriaField<?>>> r = new HashMap<>();
        entities.forEach((s, updateRepository) -> {
            List<CriteriaField<?>> newFile = new LinkedList<>(criteria);
            filter.forEach(criteriaField -> {
                String name = criteriaField.getName();
                if (name.startsWith(s)) {

                    SetField setField = new SetField("street","NARKOTYKI");
                    newFile.add(setField);
                }
            });
            r.put(updateRepository, newFile);

        });

        r.forEach((updateRepository, criteriaFields) -> {
            List<Long> update = updateRepository.update(providerSpecification1(criteriaFields));
            updated.addAll(update);
        });
        return new ArrayList<>(updated);
    }

    private SpecificationUpdate providerSpecification1(List<CriteriaField<?>> criteriaFields) {
        return new UpdateCriteriaSpecification<>(new DistributeCriteriaFactory<>(criteriaFields, map, map, map));
    }


    @Override
    public SpecificationUpdate<MODEL> providerSpecification(List<Field<?>> fields) {
        return new UpdateCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map, map, map));
    }


}
