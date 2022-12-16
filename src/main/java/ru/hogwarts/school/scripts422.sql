CREATE TABLE person
(
    id      REAL PRIMARY KEY,
    name    TEXT,
    age     int CHECK ( age > 0 ),
    license BOOLEAN,
    car     text REFERENCES cars (id)
);
CREATE TABLE cars
(
    id    INTEGER PRIMARY KEY,
    name  TEXT,
    model TEXT,
    cost  int
);
