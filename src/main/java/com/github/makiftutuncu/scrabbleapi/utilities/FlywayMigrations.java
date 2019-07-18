package com.github.makiftutuncu.scrabbleapi.utilities;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("flywayMigrations")
public class FlywayMigrations {
    private final Flyway flyway;

    private boolean didRun = false;

    @Autowired
    public FlywayMigrations(DatabaseConfig databaseConfig) {
        this.flyway = Flyway
                .configure()
                .dataSource(
                        String.format("jdbc:postgresql://%s:%s/%s", databaseConfig.host, databaseConfig.port, databaseConfig.database),
                        databaseConfig.user,
                        databaseConfig.password
                )
                .load();

        migrate();
    }

    public void migrate() {
        if (!didRun) {
            flyway.migrate();
            didRun = true;
        }
    }
}
