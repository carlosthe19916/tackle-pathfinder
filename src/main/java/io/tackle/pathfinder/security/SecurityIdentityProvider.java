package io.tackle.pathfinder.security;

public interface SecurityIdentityProvider {
    String getUsername();

    String getLanguage(String language);
}
