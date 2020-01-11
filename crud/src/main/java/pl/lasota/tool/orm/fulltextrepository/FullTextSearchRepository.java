package pl.lasota.tool.orm.fulltextrepository;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.orm.common.EntityBase;
import pl.lasota.tool.orm.repository.Repository;

public interface FullTextSearchRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    Page<MODEL> find(SpecificationFullText specification, Pageable pageable);

    void reindex(MassIndexerProgressMonitor progress);

}
