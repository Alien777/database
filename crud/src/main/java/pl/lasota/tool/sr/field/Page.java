package pl.lasota.tool.sr.field;

public final class Page {

    public Page(int page, int limit) {
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
