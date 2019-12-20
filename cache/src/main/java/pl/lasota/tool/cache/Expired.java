package pl.lasota.tool.cache;


import java.util.Objects;

public final class Expired<VALUE> {

    private final VALUE VALUE;
    private final long life;

    Expired(final VALUE VALUE, final long life) {
        this.VALUE = VALUE;
        this.life = life;
    }

    Expired(final VALUE VALUE) {
        this.VALUE = VALUE;
        this.life = 0;
    }

    boolean isExpired() {
        return life - System.currentTimeMillis() <= 0;
    }

    VALUE getValue() {
        return VALUE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expired<?> that = (Expired<?>) o;
        return Objects.equals(VALUE, that.VALUE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(VALUE);
    }
}
