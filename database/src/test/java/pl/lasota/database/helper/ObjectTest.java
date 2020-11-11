package pl.lasota.database.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.lasota.database.mapping.NotUpdating;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectTest {

    @NotUpdating
    private String colorSecond;
}
