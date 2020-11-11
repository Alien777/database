package pl.lasota.database.service.base;

public interface SpecificationProvider<TYPE, QUERY> {

    TYPE providerSpecification(QUERY query);
}
