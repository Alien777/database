package pl.lasota.tool.crud.service.listener;

import pl.lasota.tool.crud.field.Field;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.Specification;
import pl.lasota.tool.crud.repository.mapping.FieldMapping;
import pl.lasota.tool.crud.repository.delete.DeleteRepository;
import pl.lasota.tool.crud.repository.delete.SpecificationDelete;
import pl.lasota.tool.crud.repository.delete.criteria.DeleteCriteriaSpecification;
import pl.lasota.tool.crud.repository.distributed.DistributeCriteriaFactory;
import pl.lasota.tool.crud.service.DeleteService;
import pl.lasota.tool.crud.service.SpecificationProvider;

import java.util.LinkedList;
import java.util.List;

public class ListenerDeleteService<MODEL extends EntityBase> implements DeleteService, ListenerService<List<Long>>, SpecificationProvider<Specification<MODEL>> {

    private final DeleteRepository<MODEL> repository;

    private final List<ChangeListener<List<Long>>> changeListeners = new LinkedList<>();

    public ListenerDeleteService(DeleteRepository<MODEL> repository) {
        this.repository = repository;
    }

    @Override
    public List<Long> delete(List<Field<?>> source) {
        List<Long> delete = repository.delete(providerSpecification(source));
        changeListeners.forEach(l -> l.onChange(delete, TypeListener.DELETE));
        return delete;
    }

    @Override
    public SpecificationDelete<MODEL> providerSpecification(List<Field<?>> fields) {
        FieldMapping<MODEL> map = new FieldMapping<>();
        return new DeleteCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map, map, map));
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
