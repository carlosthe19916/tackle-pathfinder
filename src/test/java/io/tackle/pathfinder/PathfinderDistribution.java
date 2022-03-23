package io.tackle.pathfinder;

public enum PathfinderDistribution {
    standalone(null),
    oidc("oidc");

    private final String quarkusProfileName;

    PathfinderDistribution(String quarkusProfileName) {
        this.quarkusProfileName = quarkusProfileName;
    }

    public String getQuarkusProfileName() {
        return quarkusProfileName;
    }

}
