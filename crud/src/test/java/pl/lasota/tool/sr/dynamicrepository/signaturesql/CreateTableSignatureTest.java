package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import org.junit.Test;
import pl.lasota.tool.sr.dynamicrepository.partsql.column.Column;
import pl.lasota.tool.sr.dynamicrepository.partsql.constrains.PrimaryKey;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Serial;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Text;
import pl.lasota.tool.sr.dynamicrepository.partsql.datatype.Varchar;

import static org.assertj.core.api.Assertions.assertThat;


public class CreateTableSignatureTest {

    @Test
    public void testCreateTableWithOneColumn() {
        Column id = new Column("id", new Serial(), new PrimaryKey());
        CreateTableSignature tableSignature = new CreateTableSignature("tabelka", id);
        assertThat(tableSignature.create()).isEqualTo("CREATE TABLE tabelka (id SERIAL PRIMARY KEY);");

    }

    @Test
    public void testCreateTableWithFourColumn() {
        Column id = new Column("id", new Serial(), new PrimaryKey());
        Column test = new Column("test", new Varchar());
        Column test2 = new Column("test22", new Varchar());
        Column test1 = new Column("varek", new Text());
        CreateTableSignature tableSignature = new CreateTableSignature("tabelka", id,  test, test2, test1);
        assertThat(tableSignature.create()).isEqualTo("CREATE TABLE tabelka (id SERIAL PRIMARY KEY,\n" +
                "test VARCHAR,\n" +
                "test22 VARCHAR,\n" +
                "varek TEXT);");

    }
}
