package pl.lasota.tool.sr.repository.query;

public class Test {

    public static void main(String[] args) {
        CriteriaBuilderImpl where = CriteriaBuilderImpl.criteria();

        Predicatable and = where.root(where.and(where.like("212","dsa")) );

        System.out.println(and.predicate(null,null,null));

    }

}
