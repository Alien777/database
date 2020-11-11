package pl.lasota.database.repository.query.field;

import lombok.Getter;

@Getter
public class SetField {

    private final String path;
    private final Object value;

    public SetField(String path, Object value) {
        this.path = path;
        this.value = value;
    }
}
