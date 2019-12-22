package pl.lasota.tool.crud.repository;

import org.springframework.lang.Nullable;

import java.util.List;

public interface UpdateRepository<MODEL> {

    List<MODEL> update(Specification<MODEL> specification);

}
