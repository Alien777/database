package pl.lasota.tool.crud.repository.field;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public final class Range<T> {
   private T minimum;
   private T maximum;

}
