CREATE TABLE "words"(
    "id"    SERIAL  PRIMARY KEY,
    "word"  TEXT    NOT NULL UNIQUE
);

CREATE TABLE "boards"(
    "id"        SERIAL  PRIMARY KEY,
    "name"      TEXT    NOT NULL UNIQUE,
    "size"      INTEGER NOT NULL,
    "is_active" BOOLEAN NOT NULL
);
