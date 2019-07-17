# API Documentation

Here is an overview of the APIs:

| Method | URL                        | Link                                |
| ------ | -------------------------- | ----------------------------------- |
| POST   | /boards                    | [Jump](#post-boards)                |
| GET    | /boards                    | [Jump](#get-boards)                 |
| GET    | /boards/`id`               | [Jump](#get-boardsid)               |
| POST   | /boards/`id`/deactivate    | [Jump](#post-boardsiddeactivate)    |
| POST   | /boards/`id`/moves         | [Jump](#post-boardsidmoves)         |
| POST   | /boards/`id`/multipleMoves | [Jump](#post-boardsidmultiplemoves) |
| GET    | /boards/`id`/moves         | [Jump](#get-boardsidmoves)          |
| GET    | /boards/`id`/moves/`step`  | [Jump](#get-boardsidmovesstep)      |
| GET    | /boards/`id`/words         | [Jump](#get-boardsidwords)          |

All handled errors return with a 4xx or 5xx HTTP status containing a Json in following format:

```json
{
  "message": "Some description of the error"
}
```

All successful responses will have `200 OK` status unless explicitly mentioned.

Below are more details about each endpoint.

---

### POST /boards

Creates a new board with given data and returns created board as a Json object

#### Example Payload

```json
{
    "name": "Test",
    "size": 8
}
```

If `name` is not given, a random one will be generated. If `size` is not given, default value `15` will be used.

#### Example Successful Response

```json
{
    "id": 1,
    "isActive": true,
    "name": "Test",
    "points": 0,
    "size": 8,
    "words": []
}
```

#### Possible Errors

| What           | When                                     |
| -------------- | ---------------------------------------- |
| Already exists | There is already a board with given name |

---

### GET /boards

Returns a list of all active boards as a Json array

#### Example Successful Response

```json
[
    {
        "id": 1,
        "isActive": true,
        "name": "Test",
        "points": 5,
        "size": 8,
        "words": [
            {
                "points": 5,
                "word": "elma"
            }
        ]
    },
    {
        "id": 2,
        "isActive": true,
        "name": "Test2",
        "points": 0,
        "size": 4,
        "words": []
    }
]
```

---

### GET /boards/`id`

Returns a board as a Json object

#### Example Successful Response

```json
{
    "id": 1,
    "isActive": true,
    "name": "Test",
    "points": 0,
    "size": 8,
    "words": []
}
```

#### Possible Errors

| What      | When                            |
| --------- | ------------------------------- |
| Not found | There is no board with given id |

---

### POST /boards/`id`/deactivate

Deactivates given board and returns deactivated board as a Json object

#### Example Successful Response

```json
{
    "id": 1,
    "isActive": false,
    "name": "Test",
    "points": 0,
    "size": 8,
    "words": []
}
```

#### Possible Errors

| What      | When                            |
| --------- | ------------------------------- |
| Not found | There is no board with given id |

---

### POST /boards/`id`/moves

Creates a new move on given board with given data and returns created move as a Json object

#### Example Payload

```json
{
    "column": 2,
    "isHorizontal": false,
    "row": 1,
    "word": "ekmek"
}
```

#### Example Successful Response

```json
{
    "column": 2,
    "isHorizontal": false,
    "points": 6,
    "row": 1,
    "word": "ekmek"
}
```

#### Possible Errors

| What       | When                                                                 |
| ---------- | -------------------------------------------------------------------- |
| Not found  | There is no board with given id                                      |
| Cannot add | Cannot add word, see rules in [introduction](README.md#introduction) |

---

### POST /boards/`id`/multipleMoves

Creates new moves on given board with given data and returns created moves as a Json array

Only adds moves if all moves are valid 

#### Example Payload

```json
[
    {
        "column": 2,
        "isHorizontal": false,
        "row": 1,
        "word": "ekmek"
    },
    {
        "column": 2,
        "isHorizontal": true,
        "row": 1,
        "word": "elma"
    }
]
```

#### Example Successful Response

```json
[
    {
        "column": 2,
        "isHorizontal": false,
        "row": 1,
        "word": "ekmek",
        "points": 6
    },
    {
        "column": 2,
        "isHorizontal": true,
        "row": 1,
        "word": "elma",
        "points": 5
    }
]
```

#### Possible Errors

| What       | When                                                                  |
| ---------- | --------------------------------------------------------------------- |
| Not found  | There is no board with given id                                       |
| Cannot add | Cannot add words, see rules in [introduction](README.md#introduction) |

---

### GET /boards/`id`/moves

Returns moves of a board as a Json array

#### Example Successful Response

```json
[
    {
        "column": 2,
        "isHorizontal": true,
        "points": 5,
        "row": 1,
        "word": "elma"
    },
    {
        "column": 2,
        "isHorizontal": false,
        "points": 6,
        "row": 1,
        "word": "ekmek"
    }
]
```

#### Possible Errors

| What      | When                            |
| --------- | ------------------------------- |
| Not found | There is no board with given id |

---

### GET /boards/`id`/moves/`step`

Returns moves of a board at given step as a Json array

#### Example Successful Response

```json
[
    {
        "column": 2,
        "isHorizontal": true,
        "points": 5,
        "row": 1,
        "word": "elma"
    }
]
```

#### Possible Errors

| What      | When                            |
| --------- | ------------------------------- |
| Not found | There is no board with given id |

---

### GET /boards/`id`/words

Returns words of a board as a Json array

#### Example Successful Response

```json
[
    {
        "points": 5,
        "word": "elma"
    },
    {
        "points": 6,
        "word": "ekmek"
    }
]
```

#### Possible Errors

| What      | When                            |
| --------- | ------------------------------- |
| Not found | There is no board with given id |
