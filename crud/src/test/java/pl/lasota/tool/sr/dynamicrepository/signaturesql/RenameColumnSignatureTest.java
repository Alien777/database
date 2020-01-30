package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RenameColumnSignatureTest {

    @Test
    public void testRenameColumn() {
        RenameColumnSignature renameTableSignature =
                new RenameColumnSignature("tabela_nazwa", "stara_kolumna","nowa_kolumna");
        assertThat(renameTableSignature.create()).isEqualTo("ALTER TABLE tabela_nazwa RENAME COLUMN stara_kolumna TO nowa_kolumna;");
    }
}
