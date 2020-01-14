package pl.lasota.tool.sr.repository.tokensearch;

import org.hibernate.search.batchindexing.MassIndexerProgressMonitor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.repository.EntityBase;
import pl.lasota.tool.sr.repository.Repository;
import pl.lasota.tool.sr.repository.tokensearch.specification.SpecificationSearchToken;

public interface TokenSearchRepository<MODEL extends EntityBase> extends Repository<MODEL> {

    Page<MODEL> find(SpecificationSearchToken specification, Pageable pageable);

    void reindex(MassIndexerProgressMonitor progress);

}
