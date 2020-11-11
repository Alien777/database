package pl.lasota.database.security;

public interface SecurityProvider {
    Entitlement getEntitlement();

    void setEntitlement(Entitlement entitlement);
}
