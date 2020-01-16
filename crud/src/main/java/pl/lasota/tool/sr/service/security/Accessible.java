package pl.lasota.tool.sr.service.security;

public interface Accessible {

    Accessible read();

    Accessible update();

    Accessible delete();

    Accessible one();

    Accessible zero();

}
