package com.github.makiftutuncu.scrabbleapi.utilities;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

@Component
public class FlywayMigrations {
    private final String host     = "localhost";
    private final int port        = 5432;
    private final String database = "scrabble";
    private final String user     = "scrabble-user";
    private final String password = "scrabble-pass";

    private Flyway flyway = Flyway
            .configure()
            .dataSource(
                    String.format("jdbc:postgresql://%s:%d/%s", host, port, database),
                    user,
                    password
            )
            .load();

    public FlywayMigrations() {
        flyway.migrate();
    }
}
