package pl.lasota.tool.crud.service.listener;


import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.mapping.Mapping;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.mapping.FieldMapping;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.repository.update.SpecificationUpdate;
import pl.lasota.tool.crud.repository.update.UpdateRepository;
import pl.lasota.tool.crud.repository.update.criteria.UpdateCriteriaSpecification;
import pl.lasota.tool.crud.service.SpecificationProvider;
import pl.lasota.tool.crud.service.UpdateService;

import java.util.LinkedList;
import java.util.List;

public class ListenerUpdateService<READING, MODEL extends EntityBase> implements UpdateService, ListenerService<List<Long>>, SpecificationProvider<Specification<MODEL>> {

    private final List<ChangeListener<List<Long>>> changeListeners = new LinkedList<>();

    private final UpdateRepository<MODEL> repository;
    private final FieldMapping<MODEL> map;

    public ListenerUpdateService(UpdateRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        map = new FieldMapping<>(modelClass);
    }

    @Override
    public List<Long> update(List<Field<?>> source) {
        List<Long> update = repository.update(providerSpecification(source));
        changeListeners.forEach(l -> l.onChange(update, TypeListener.UPDATE));
        return update;
    }


    @Override
    public SpecificationUpdate<MODEL> providerSpecification(List<Field<?>> fields) {

        return new UpdateCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map, map, map));
    }

    @Override
    public void add(ChangeListener<List<Long>> change) {
        if (change == null) {
            return;
        }
        changeListeners.add(change);
    }

    @Override
    public void remove(ChangeListener<List<Long>> change) {
        changeListeners.remove(change);
    }
}
