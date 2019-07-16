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

CREATE TABLE "moves"(
    "id"            SERIAL  PRIMARY KEY,
    "board_id"      INTEGER NOT NULL REFERENCES "boards"("id"),
    "word_id"       INTEGER NOT NULL REFERENCES "words"("id"),
    "row"           INTEGER NOT NULL,
    "column"        INTEGER NOT NULL,
    "is_horizontal" BOOLEAN NOT NULL,
    UNIQUE("board_id", "word_id")
);
