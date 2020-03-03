package pl.lasota.tool.sr.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pl.lasota.tool.sr.field.definition.Field;
import pl.lasota.tool.sr.field.definition.PageField;

import java.util.List;
import java.util.Optional;

public interface Search<READING> {

    Page<READING> find(List<Field<?>> source, Pageable pageable);

    default Page<READING> find(List<Field<?>> source) {
        Optional<PageField> first = source.stream().filter(field -> field instanceof PageField)
                .map(field -> (PageField) field).findFirst();

        PageField pageField = first.orElse(new PageField(new pl.lasota.tool.sr.field.Page(0, 10)));

        pl.lasota.tool.sr.field.Page page = pageField.getValue();
        return this.find(source, PageRequest.of(page.getPage(), page.getLimit()));
    }
}
