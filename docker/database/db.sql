DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(250) NOT NULL,
    email      VARCHAR(250) NOT NULL UNIQUE,
    password   VARCHAR(250) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    updated_at TIMESTAMP
);