package io.tackle.pathfinder.security;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.lang.annotation.Annotation;

@ApplicationScoped
public class SecurityIdentityManager {

    @ConfigProperty(name = "pathfinder.security.type")
    String securityType;

    @Inject
    @Any
    Instance<SecurityIdentityProvider> providers;

    public SecurityIdentityProvider getSecurityIdentityProvider() {
        SecurityIdentityType.Type type = SecurityIdentityType.Type.valueOf(securityType.toUpperCase());
        Annotation annotation = new SecurityIdentityLiteral(type);
        return providers.select(annotation).get();
    }

}
