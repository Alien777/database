package pl.lasota.tool.sr.service.security;

public interface ConfigurationAccessibleChmod {

    void owner(Accessible accessible);

    void group(Accessible accessible);

    void other(Accessible accessible);
}
