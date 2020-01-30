package pl.lasota.tool.sr.dynamicrepository.signaturesql;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class RenameTableSignatureTest {

    @Test
    public void testRenameTable() {
        RenameTableSignature renameTableSignature = new RenameTableSignature("stara_nazwa", "nowa_nazwa");
        assertThat(renameTableSignature.create()).isEqualTo("ALTER TABLE stara_nazwa RENAME TO nowa_nazwa;");
    }
}
