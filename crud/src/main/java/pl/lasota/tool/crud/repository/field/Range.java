package pl.lasota.tool.crud.repository.field;

public final class Range<T> {
   private T minimum;
   private T maximum;

   public Range(T minimum, T maximum) {
      this.minimum = minimum;
      this.maximum = maximum;
   }

   public T getMinimum() {
      return minimum;
   }

   public T getMaximum() {
      return maximum;
   }
}
