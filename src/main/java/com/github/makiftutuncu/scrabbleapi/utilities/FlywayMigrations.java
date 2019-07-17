package com.github.makiftutuncu.scrabbleapi.utilities;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FlywayMigrations {
    @Autowired
    public FlywayMigrations(DatabaseConfig databaseConfig) {
        Flyway flyway = Flyway
                .configure()
                .dataSource(
                        String.format("jdbc:postgresql://%s:%s/%s", databaseConfig.host, databaseConfig.port, databaseConfig.database),
                        databaseConfig.user,
                        databaseConfig.password
                )
                .load();

        flyway.migrate();
    }
}
