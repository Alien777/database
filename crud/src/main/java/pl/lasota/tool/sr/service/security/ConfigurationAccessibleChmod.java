package pl.lasota.tool.sr.service.security;

public interface ConfigurationAccessibleChmod {

    void user(Accessible accessible);

    void group(Accessible accessible);

    void other(Accessible accessible);
}
