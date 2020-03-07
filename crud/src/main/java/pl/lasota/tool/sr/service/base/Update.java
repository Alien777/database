package pl.lasota.tool.sr.service.base;

import pl.lasota.tool.sr.repository.query.QueryUpdate;

import java.util.List;

public interface Update {

    List<Long> update(QueryUpdate queryUpdate);

}
