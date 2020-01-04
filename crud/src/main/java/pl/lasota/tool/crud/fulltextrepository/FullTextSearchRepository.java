package pl.lasota.tool.crud.fulltextrepository;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.crud.common.EntityBase;
import pl.lasota.tool.crud.repository.Repository;
import pl.lasota.tool.crud.repository.search.SearchRepository;

public interface FullTextSearchRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    Page<MODEL> find(SpecificationFullText specification, Pageable pageable);

    void reindex(MassIndexerProgressMonitor progress);

}
