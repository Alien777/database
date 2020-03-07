package pl.lasota.tool.sr.service.base;

public interface SpecificationProvider<TYPE, QUERY> {

    TYPE providerSpecification(QUERY query);
}
