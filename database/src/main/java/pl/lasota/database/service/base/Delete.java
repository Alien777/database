package pl.lasota.database.service.base;

import pl.lasota.database.repository.query.QueryDelete;

import java.util.List;

public interface Delete {

    List<Long> delete(QueryDelete source);
}
