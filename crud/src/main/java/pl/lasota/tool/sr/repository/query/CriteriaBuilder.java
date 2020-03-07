package pl.lasota.tool.sr.repository.query;

import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.repository.query.field.Field;
import pl.lasota.tool.sr.repository.query.field.SetField;
import pl.lasota.tool.sr.repository.query.normalize.Normalizable;
import pl.lasota.tool.sr.repository.query.sort.Sortable;

public interface CriteriaBuilder {


    Predicatable and(Field... expression);

    Predicatable or(Field... expression);

    Predicatable root(Expression... expression);

    Predicatable all();

    Field equals(String path, Object object);

    Field notEquals(String path, Object object);

    Field lt(String path, Number number);

    Field le(String path, Number number);

    Field gt(String path, Number number);

    Field ge(String path, Number number);

    Field like(String path, String number);

    Field likeR(String path, String number);

    Field likeL(String path, String number);

    Field like(String path, String number, Normalizable... normalizable);

    Field likeR(String path, String number, Normalizable... normalizable);

    Field likeL(String path, String number, Normalizable... normalizable);

    Field range(String path, Number min, Number max);


    Sortable sort(String path, Sortable.Sort sort);

    Sortable sort(String path);

    Pageable page(int page, int size);

    Pageable page(int page);

    Normalizable lowerCase();

    Normalizable upperCase();

    Normalizable accent();

    Updatable set(SetField... setField);


}
