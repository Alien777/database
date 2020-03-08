package pl.lasota.tool.sr.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lasota.tool.sr.mapping.NotUpdating;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectTest {

    @NotUpdating
    private String colorSecond;
}
