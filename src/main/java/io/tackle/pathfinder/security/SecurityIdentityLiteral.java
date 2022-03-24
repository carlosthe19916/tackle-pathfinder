package io.tackle.pathfinder.security;

import javax.enterprise.util.AnnotationLiteral;

public class SecurityIdentityLiteral extends AnnotationLiteral<SecurityIdentityType> implements SecurityIdentityType {

    private final Type type;

    public SecurityIdentityLiteral(Type type) {
        this.type = type;
    }

    @Override
    public Type value() {
        return type;
    }
}
