package com.elmeftouhi.expensez.common;

import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.FluentConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayConfigurationCustomizer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.file.Files;

/**
 * Add workaround for the CICD limitation to apply support a single flyway location.
 * We cannot separate postgres and common sql files
 *
 * Therefor we filter out the postgres files at the start of test
 */

@TestConfiguration
public class FlywayConfiguration implements FlywayConfigurationCustomizer {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void customize(FluentConfiguration configuration) {
        try {
            var tmpdir = Files.createTempDirectory("flyway");
            for (Location oldPath : configuration.getLocations()) {
                for (Resource resource : applicationContext.getResources(oldPath + "/*.sql")) {
                    if (resource.getFilename() != null && !resource.getFilename().contains("psql")) {
                        Files.copy(resource.getFile().toPath(), tmpdir.resolve(resource.getFilename()));
                    }
                }
            }
            configuration.locations("filesystems:" + tmpdir.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
