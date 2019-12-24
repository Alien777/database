package pl.lasota.tool.crud.field;

public final class Pageable {

    public Pageable(int page, int limit) {
        this.page = Math.max(page, 0);
        this.limit = limit <= 0 ? 1 : limit;
    }

    private int page;
    private int limit;

    public int getPage() {
        return page;
    }

    public int getLimit() {
        return limit;
    }
}
