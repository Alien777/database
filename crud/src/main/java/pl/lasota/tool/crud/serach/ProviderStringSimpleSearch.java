package pl.lasota.tool.crud.serach;


public class ProviderStringSimpleSearch<T> extends ProviderSimpleSearch<T, String> {

    public ProviderStringSimpleSearch(String formula) throws Exception {
        super(formula, new ParserFormula());
    }
}
