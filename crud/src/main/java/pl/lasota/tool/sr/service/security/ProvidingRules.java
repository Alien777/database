package pl.lasota.tool.sr.service.security;

import com.google.common.collect.ImmutableSet;
import pl.lasota.tool.sr.security.AccessContext;

public interface ProvidingRules {

    ImmutableSet<Short> forCanRead();

    ImmutableSet<Short> forCanUpdate();

    ImmutableSet<Short> forCanDelete();

    AccessContext create(ConfigurationAccessible configurationAccessible, String name);

    AccessContext create(String name);

}
