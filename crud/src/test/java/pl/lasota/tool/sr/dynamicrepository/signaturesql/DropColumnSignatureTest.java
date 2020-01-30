package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class DropColumnSignatureTest {

    @Test
    public void testOneDropColumn() {
        CreatableSql addColumnSignature = new DropColumnSignature("tabelka", "kolumna");
        assertThat(addColumnSignature.create()).isEqualTo("ALTER TABLE tabelka DROP COLUMN kolumna;");
    }

    @Test
    public void testThreeDropColumn() {
        CreatableSql addColumnSignature = new DropColumnSignature("tabelka", "kolumna", "kolumna2", "kolumna3");
        assertThat(addColumnSignature.create()).isEqualTo("ALTER TABLE tabelka DROP COLUMN kolumna,\n" +
                " DROP COLUMN kolumna2,\n" +
                " DROP COLUMN kolumna3;");
    }
}
