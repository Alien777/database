package pl.lasota.tool.sr.repository.query.sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

public interface Sortable  {
    enum Sort {
        ASC, DESC
    }

    Order order(Class<?> model, Root<?> root, CriteriaBuilder criteriaBuilder);
}
