package io.tackle.pathfinder.security;

import io.quarkus.security.identity.SecurityIdentity;
import io.tackle.pathfinder.services.TranslatorSvc;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

@RequestScoped
@SecurityIdentityType(SecurityIdentityType.Type.OIDC)
public class OIDCSecurityIdentityProvider implements SecurityIdentityProvider {

    @Inject
    SecurityIdentity identityContext;

    @Inject
    JsonWebToken accessToken;

    @Inject
    TranslatorSvc translatorSvc;

    @Override
    public String getUsername() {
        return identityContext.getPrincipal().getName();
    }

    @Override
    public String getLanguage(String language) {
        return translatorSvc.getLanguage(accessToken.getRawToken(), language);
    }

}
