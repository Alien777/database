package pl.lasota.tool.crud.mapping;

public interface Mapping<SOURCE, DESTINATION> {
    DESTINATION mapper(SOURCE source);
}
