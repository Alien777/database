package pl.lasota.database.repository.query.normalize;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

/*
CREATE OR REPLACE FUNCTION postgres.cityon.f_unaccent(text)
    RETURNS text AS
$func$
SELECT public.unaccent('public.unaccent', $1)  -- schema-qualify function and dictionary
$func$  LANGUAGE sql IMMUTABLE;
 */

public class AcccentNormalizer implements Normalizable {
    @Override
    public Expression<String> normalize(Expression<String> objectPath, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.function("f_unaccent", String.class, objectPath);
    }

    @Override
    public String normalize(String value) {
        return StringUtils.stripAccents(value);
    }

}
