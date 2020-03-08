package pl.lasota.tool.sr.security;

public interface SecurityProvider {
    Entitlement getEntitlement();

    void setEntitlement(Entitlement entitlement);

}
