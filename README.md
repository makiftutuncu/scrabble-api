# Scrabble API

## Table of Contents

1. [Introduction](#introduction)
3. [Development and Running](#development-and-running)
4. [Testing](#testing)
5. [Contributing](#contributing)
6. [License](#license)

## Introduction

Scrabble API is backend application written in Java. It is a REST API for a simplified Scrabble word game with Turkish words.

At its core, Scrabble API is a [Spring MVC](https://spring.io/) app. It uses [Hibernate](https://hibernate.org/) for persistance to a [PostgreSQL](https://www.postgresql.org/) database. See [Development and Running](#development-and-running) for setting it up.

A detailed API documentation is in [API.md](API.md).

Here are rules and assumptions taken into account for the implementation of Scrabble API:

* There can be multiple (game) boards.
* A board can be disabled, if so, it cannot be re-enabled or modified.
* Valid Turkish word(s) can be placed on a given board.
* A word can be placed (i.e. making a move) anywhere on the board if the board is empty.
* A word can be placed starting from an existing letter if the board is not empty.
* Scoring is based on the points of letters of the words on the board, see [points of letters](LETTER_POINTS.md).
* Boards, moves and words can be listed.

## Development and Running

Scrabble API is built with [Gradle](https://gradle.org/) and it runs on [Apache Tomcat](http://tomcat.apache.org/) application server via [Gretty](http://akhikhl.github.io/gretty-doc/index.html) plugin.

To clean, compile, run and debug Scrabble API, you can use Gradle tasks `clean`, `compile`, `appRun` and `appRunDebug`. 

Before running the application for the first time, you will need to set up the database. In order to easily do that, you can use [Docker](https://www.docker.com/)'s `docker-compose` by running in project's root directory:

```docker-compose up -d```

The database schema will automatically be migrated via [Flyway](https://flywaydb.org/).

HTTP and debug ports, application server etc. can be configured in [build.gradle](build.gradle).

Database credentials can be configured in [database.properties](src/main/resources/database.properties) and [hibernate.cfg.xml](src/main/resources/hibernate.cfg.xml) for running the application. For testing, they are in [database.test.properties](src/test/resources/database.test.properties) and [hibernate.cfg.xml](src/test/resources/hibernate.cfg.xml). Please note that default configuration is aligned with [docker-compose.yml](docker-compose.yml) so you might need to update it as well. 

## Testing

Scrabble API contains [JUnit](https://junit.org/junit4/) tests. To run all the tests, use `test` task of Gradle.

## Contributing

Please feel free to send a pull request for your contributions. Thank you.

## License

Scrabble API is licensed with MIT License. See [LICENSE.md](LICENSE.md) for details.
