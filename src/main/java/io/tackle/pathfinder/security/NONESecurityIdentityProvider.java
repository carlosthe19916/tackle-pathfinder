package io.tackle.pathfinder.security;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import java.util.Optional;

@RequestScoped
@SecurityIdentityType(SecurityIdentityType.Type.NONE)
public class NONESecurityIdentityProvider implements SecurityIdentityProvider {

    @ConfigProperty(name = "pathfinder.security.default-username")
    String defaultUsername;

    @ConfigProperty(name = "pathfinder.default-language")
    String defaultLanguage;

    @Override
    public String getUsername() {
        return defaultUsername;
    }

    @Override
    public String getLanguage(String language) {
        return Optional.ofNullable(language).orElse(defaultLanguage);
    }

}
