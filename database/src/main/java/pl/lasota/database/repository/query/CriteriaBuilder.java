package pl.lasota.database.repository.query;

import org.springframework.data.domain.Pageable;
import pl.lasota.database.repository.query.field.SetField;
import pl.lasota.database.repository.query.normalize.Normalizable;
import pl.lasota.database.repository.query.sort.Sortable;
import pl.lasota.database.repository.query.field.Field;

public interface CriteriaBuilder {


    Predicatable and(Field... expression);

    Predicatable or(Field... expression);

    Predicatable root(Expression... expression);

    Predicatable all();

    Field equals(String path, Object object);

    <T extends String> Field equals(String path, T value, Normalizable... normalizable);

    Field notEquals(String path, Object object);

    Field lt(String path, Number number);

    Field le(String path, Number number);

    Field gt(String path, Number number);

    Field ge(String path, Number number);

    Field like(String path, String value);

    Field likeR(String path, String value);

    Field likeL(String path, String value);

    Field like(String path, String value, Normalizable... normalizable);

    Field likeR(String path, String value, Normalizable... normalizable);

    Field likeL(String path, String value, Normalizable... normalizable);

    Field range(String path, Number min, Number max);


    Sortable sort(String path, Sortable.Sort sort);

    Sortable sort(String path);

    Pageable page(int page, int size);

    Pageable page(int page);

    Normalizable lowerCase();

    Normalizable upperCase();

    Normalizable accent();

    SetField update(String path, Object object);

    Updatable set(SetField... setField);


}
