package pl.lasota.tool.crud.serach;


public interface Parser<S, D> {

    D parse(S source) throws Exception;
}
