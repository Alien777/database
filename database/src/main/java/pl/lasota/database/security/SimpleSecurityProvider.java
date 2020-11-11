package pl.lasota.database.security;

public class SimpleSecurityProvider implements SecurityProvider {

    private Entitlement entitlement;

    @Override
    public Entitlement getEntitlement() {
        return entitlement;
    }

    @Override
    public void setEntitlement(Entitlement entitlement) {
        this.entitlement = entitlement;
    }
}
