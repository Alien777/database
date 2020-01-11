package pl.lasota.tool.orm.parser;

public interface Parser<S, D> {
    D parse(S source) throws Exception;
}
