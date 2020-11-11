package pl.lasota.database.service.base;

import org.springframework.data.domain.Page;
import pl.lasota.database.repository.query.QueryCriteria;

public interface Search<READING> {

    Page<READING> find(QueryCriteria queryCriteria);

    Page<READING> findCount(QueryCriteria queryCriteria);

}
