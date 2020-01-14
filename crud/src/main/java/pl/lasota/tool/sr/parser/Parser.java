package pl.lasota.tool.sr.parser;

public interface Parser<S, D> {
    D parse(S source) throws Exception;
}
