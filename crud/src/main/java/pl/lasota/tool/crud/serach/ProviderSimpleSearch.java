package pl.lasota.tool.crud.serach;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProviderSimpleSearch<T> implements PredicateSupplier<T> {

    private final List<Field> parser;
    private final Map<String, String> aliasToEntity;

    public ProviderSimpleSearch(String formula, ParserFormula parserFormula) throws ParseException {
        this(formula, parserFormula, new HashMap<>());
    }

    public ProviderSimpleSearch(String formula, ParserFormula parserFormula, Map<String, String> aliasToEntity) throws ParseException {
        this.aliasToEntity = aliasToEntity;
        this.parser = parserFormula.parser(formula);
    }

    @Override
    public void predicateAnd(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder) {

    }

    @Override
    public void predicateOr(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder) {

    }

}
