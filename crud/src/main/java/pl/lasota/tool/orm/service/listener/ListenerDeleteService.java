package pl.lasota.tool.orm.service.listener;

import pl.lasota.tool.orm.field.Field;
import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Specification;
import pl.lasota.tool.orm.repository.CriteriaFieldMapping;
import pl.lasota.tool.orm.repository.delete.DeleteRepository;
import pl.lasota.tool.orm.repository.delete.SpecificationDelete;
import pl.lasota.tool.orm.repository.delete.criteria.DeleteCriteriaSpecification;
import pl.lasota.tool.orm.repository.DistributeCriteriaFactory;
import pl.lasota.tool.orm.service.DeleteService;
import pl.lasota.tool.orm.service.SpecificationProvider;

import java.util.LinkedList;
import java.util.List;

public class ListenerDeleteService<MODEL extends EntityBase> implements DeleteService, ListenerService<List<Long>>, SpecificationProvider<Specification<MODEL>> {

    private final DeleteRepository<MODEL> repository;

    private final List<ChangeListener<List<Long>>> changeListeners = new LinkedList<>();
    private final CriteriaFieldMapping<MODEL> map;

    public ListenerDeleteService(DeleteRepository<MODEL> repository, Class<MODEL> modelClass) {
        this.repository = repository;
        map = new CriteriaFieldMapping<>(modelClass);
        repository.modelClass(modelClass);
    }

    @Override
    public List<Long> delete(List<Field<?>> source) {
        List<Long> delete = repository.delete(providerSpecification(source));
        changeListeners.forEach(l -> l.onChange(delete, TypeListener.DELETE));

        return delete;
    }

    @Override
    public SpecificationDelete<MODEL> providerSpecification(List<Field<?>> fields) {

        return new DeleteCriteriaSpecification<>(new DistributeCriteriaFactory<>(filter(fields), map));
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
