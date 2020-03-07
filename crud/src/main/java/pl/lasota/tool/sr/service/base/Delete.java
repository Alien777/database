package pl.lasota.tool.sr.service.base;

import pl.lasota.tool.sr.repository.query.QueryDelete;

import java.util.List;

public interface Delete {

    List<Long> delete(QueryDelete source);
}
