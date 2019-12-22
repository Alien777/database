package pl.lasota.tool.crud.serach.criteria;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public interface CriteriaSetSupplier<MODEL>  {

    void set(CriteriaUpdate criteriaUpdate);
}
