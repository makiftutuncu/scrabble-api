package com.github.makiftutuncu.scrabbleapi.utilities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:database.properties")
public class DatabaseConfig {
    @Value("${database.host}")
    public String host;

    @Value("${database.port}")
    public String port;

    @Value("${database.name}")
    public String database;

    @Value("${database.user}")
    public String user;

    @Value("${database.pass}")
    public String password;
}
