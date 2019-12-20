package pl.lasota.tool.crud.serach;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderSimpleSearch<T, SOURCE> implements PredicateSupplier<T> {

    private final List<Field> parser;
    private final Map<String, String> aliasToEntity;


    public ProviderSimpleSearch(SOURCE formula, Parser<SOURCE,List<Field>> parserFormula) throws Exception {
        this(formula, parserFormula, new HashMap<>());
    }

    public ProviderSimpleSearch(SOURCE formula, Parser<SOURCE,List<Field>> parserFormula, Map<String, String> aliasToEntity) throws Exception {
        this.aliasToEntity = aliasToEntity;
        this.parser = parserFormula.parse(formula);
    }

    @Override
    public void predicateAnd(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder) {

    }

    @Override
    public void predicateOr(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder) {

    }

}
