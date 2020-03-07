package pl.lasota.tool.sr.repository.query;

import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.repository.query.sort.Sortable;

public interface BuilderQuery {

    QueryCriteria build(Predicatable predicate, Pageable pageable);

    QueryCriteria build(Predicatable predicatable, Sortable sortable, Pageable pageable);

    QueryDelete build(Predicatable predicatable);

    QueryUpdate build(Predicatable predicatable, Updatable updatable);

}
