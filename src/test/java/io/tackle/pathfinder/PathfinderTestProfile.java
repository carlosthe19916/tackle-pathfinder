/*
 * Copyright 2019 Project OpenUBL, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.tackle.pathfinder;

import io.quarkus.test.junit.QuarkusTestProfile;
import io.tackle.commons.testcontainers.KeycloakTestResource;
import io.tackle.commons.testcontainers.PostgreSQLDatabaseTestResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

public class PathfinderTestProfile implements QuarkusTestProfile {

    PathfinderDistribution distribution;
    List<TestResourceEntry> testResources = new ArrayList<>();
    Map<String, String> configOverrides = new HashMap<>();

    public PathfinderTestProfile() {
        String distributionValue = System.getProperty("pathfinder.distribution.value", PathfinderDistribution.standalone.toString());
        PathfinderDistribution distribution = PathfinderDistribution.valueOf(distributionValue.toLowerCase());

        init(distribution);
    }

    public PathfinderTestProfile(PathfinderDistribution distribution) {
        init(distribution);
    }

    private void init(PathfinderDistribution distribution) {
        this.distribution = distribution;

        // Common Test resources
        testResources.add(new TestResourceEntry(PostgreSQLDatabaseTestResource.class, ofEntries(
                entry(PostgreSQLDatabaseTestResource.DB_NAME, "pathfinder_db"),
                entry(PostgreSQLDatabaseTestResource.USER, "pathfinder"),
                entry(PostgreSQLDatabaseTestResource.PASSWORD, "pathfinder")
        )));

        switch (distribution) {
            case standalone:
                // Test resources

                // Config
                configOverrides.put("quarkus.oidc.auth-server-url", "http://localhost:8180/auth"); // Since the OIDC extension is being imported in the pom.xml then we need to add the following lines
                configOverrides.put("quarkus.oidc.client-id", "backend-service");
                configOverrides.put("quarkus.oidc.credentials.secret", "secret");

                break;
            case oidc:
                // Test resources
                testResources.add(new TestResourceEntry(KeycloakTestResource.class, ofEntries(
                        entry(KeycloakTestResource.IMPORT_REALM_JSON_PATH, "keycloak/quarkus-realm.json"),
                        entry(KeycloakTestResource.REALM_NAME, "quarkus")
                )));

                // Config

                break;
        }
    }

    @Override
    public String getConfigProfile() {
        return distribution.getQuarkusProfileName() + ",test";
    }

    @Override
    public List<TestResourceEntry> testResources() {
        return testResources;
    }

    @Override
    public Map<String, String> getConfigOverrides() {
        return configOverrides;
    }

}
