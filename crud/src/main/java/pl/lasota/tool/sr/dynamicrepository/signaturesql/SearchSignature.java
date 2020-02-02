package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import pl.lasota.tool.sr.field.CriteriaField;
import pl.lasota.tool.sr.field.DistributeForField;
import pl.lasota.tool.sr.field.Field;
import pl.lasota.tool.sr.repository.DistributeForCriteria;
import pl.lasota.tool.sr.service.SpecificationProvider;

import java.util.List;
import java.util.stream.Collectors;

public class SearchSignature implements CreatableSql {

    private final String tableName;
    private final List<Field<?>> fields;
    private final DistributeForCriteria distribute;

    public SearchSignature(String tableName, List<Field<?>> fields) {
        this.tableName = tableName;
        this.fields = fields;
        distribute = new DistributeForField(fields.stream()
                .filter(field -> field instanceof CriteriaField<?>)
                .map(field -> (CriteriaField<?>) field)
                .collect(Collectors.toList()));
    }

    @Override
    public String create() {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM ");
        sql.append(tableName);
        sql.append(" WHERE 1=1 ");

        return format("SQL * FROM " + tableName);
    }


    private String createWhere()
    {

        List<CriteriaField<?>> and = distribute.and();
        List<CriteriaField<?>> or = distribute.or();
        return "";
    }

}
