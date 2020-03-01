package pl.lasota.tool.sr.service.security;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Auth {
    private String privileged;
    private short permission;
}
