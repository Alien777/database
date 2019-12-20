package pl.lasota.tool.email;

public class Placeholder {

    private final String key;

    private final String value;

    public static Placeholder of(String key, String value) {
        return new Placeholder(key, value);
    }

    private Placeholder(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
