package pl.lasota.database.service.base;

import pl.lasota.database.repository.query.QueryUpdate;

import java.util.List;

public interface Update {

    List<Long> update(QueryUpdate queryUpdate);

}
