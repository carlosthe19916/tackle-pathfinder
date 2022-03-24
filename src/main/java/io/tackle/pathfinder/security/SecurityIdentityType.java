package io.tackle.pathfinder.security;

import javax.inject.Qualifier;
import java.lang.annotation.*;

@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
@Documented
public @interface SecurityIdentityType {
    Type value();

    enum Type {
        OIDC,
        NONE
    }
}
