package pl.lasota.tool.crud.mapping;

/**
 * interface provide behavior to map object to another
 * @param <SOURCE>
 * @param <DESTINATION>
 */
public interface Mapping<SOURCE, DESTINATION> {
    DESTINATION mapper(SOURCE source);
}
