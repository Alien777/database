package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import org.junit.Test;
import pl.lasota.tool.sr.dynamicrepository.partsql.column.Column;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Integer;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Serial;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Varchar;

import static org.assertj.core.api.Assertions.assertThat;

public class AddColumnSignatureTest {

    @Test
    public void testOneColumnAdd() {
        Column id = new Column("id", new Serial());
        AddColumnSignature addColumnSignature = new AddColumnSignature("tabelka", id);
        assertThat(addColumnSignature.create()).isEqualTo("ALTER TABLE tabelka ADD COLUMN id SERIAL;");
    }

    @Test
    public void testTwoColumnAdd() {
        Column id = new Column("id", new Serial());
        Column ints = new Column("second", new Integer());
        Column aa = new Column("var", new Varchar());
        AddColumnSignature addColumnSignature = new AddColumnSignature("tabeleczka", id, ints,aa);
        assertThat(addColumnSignature.create()).isEqualTo("ALTER TABLE tabeleczka ADD COLUMN id SERIAL,\n" +
                " ADD COLUMN second INTEGER,\n" +
                " ADD COLUMN var VARCHAR;");
    }

}
