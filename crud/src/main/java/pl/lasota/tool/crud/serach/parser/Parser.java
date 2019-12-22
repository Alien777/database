package pl.lasota.tool.crud.serach.parser;


public interface Parser<S, D> {
    D parse(S source) throws Exception;
}
