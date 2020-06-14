package pl.lasota.tool.sr.service.base;

import org.springframework.data.domain.Page;
import pl.lasota.tool.sr.repository.query.QueryCriteria;

public interface Search<READING> {

    Page<READING> find(QueryCriteria queryCriteria);

    Page<READING> findCount(QueryCriteria queryCriteria);

}
