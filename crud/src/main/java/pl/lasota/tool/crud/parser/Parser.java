package pl.lasota.tool.crud.parser;


public interface Parser<S, D> {
    D parse(S source) throws Exception;
}
