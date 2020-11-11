package pl.lasota.database.repository.query.sort;

import pl.lasota.database.repository.query.Common;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public final class SimpleSort extends Common implements Sortable {
    private final String path;
    private final Sort sort;

    public SimpleSort(String path, Sort sort) {
        this.path = path;
        this.sort = sort;
    }

    public SimpleSort(String path) {
        this.path = path;
        this.sort = Sort.ASC;
    }


    @Override
    public Order order(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder) {

        Path<Object> objectPath = generatePath(path, root, model);

        Order order;
        if (sort == Sort.ASC) {
            order = criteriaBuilder.asc(objectPath);
        } else {
            order = criteriaBuilder.desc(objectPath);
        }
        return order;
    }

}
