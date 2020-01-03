package pl.lasota.tool.crud.fulltextrepository;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.repository.Repository;

public interface FullTextSearchRepository<MODEL> extends Repository<MODEL> {

    Page<MODEL> find(SpecificationFullText specification, Pageable pageable);

    void reindex(MassIndexerProgressMonitor progress);

}
