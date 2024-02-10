DROP TABLE IF EXISTS users;

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    points   INTEGER      NOT NULL DEFAULT 0
);

DROP TABLE IF EXISTS continents;

CREATE TABLE continents
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS countries;

CREATE TABLE countries
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    key          VARCHAR(255) NOT NULL,
    continent_id INTEGER      NOT NULL REFERENCES continents (id)
);

DROP TABLE IF EXISTS competitions;

CREATE TABLE competitions
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(255) NOT NULL,
    type         VARCHAR(255) NOT NULL,
    continent_id INTEGER      NOT NULL REFERENCES continents (id),
    country_id   INTEGER NULL REFERENCES countries (id)
);

DROP TABLE IF EXISTS teams;

CREATE TABLE teams
(
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    country_id INTEGER      NOT NULL REFERENCES countries (id)
);

DROP TABLE IF EXISTS team_competitions;

CREATE TABLE team_competitions
(
    id             SERIAL PRIMARY KEY,
    team_id        INTEGER NOT NULL REFERENCES teams (id),
    competition_id INTEGER NOT NULL REFERENCES competitions (id)
);

DROP TABLE IF EXISTS players;

CREATE TABLE players
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL
);

DROP TABLE IF EXISTS matches;

CREATE TABLE matches
(
    id              SERIAL PRIMARY KEY,
    home_team       INTEGER NOT NULL REFERENCES teams (id),
    away_team       INTEGER NOT NULL REFERENCES teams (id),
    competition_id  INTEGER NOT NULL REFERENCES competitions (id),
    date            DATE    NOT NULL,
    home_team_score INTEGER DEFAULT 0,
    away_team_score INTEGER DEFAULT 0
);

DROP TABLE IF EXISTS predictions;

CREATE TABLE predictions
(
    id                   SERIAL PRIMARY KEY,
    user_id              INTEGER NOT NULL REFERENCES users (id),
    match_id             INTEGER NOT NULL REFERENCES matches (id),
    home_team_prediction INTEGER,
    away_team_prediction INTEGER,
    prediction_date      DATE    NOT NULL
);

DROP TABLE IF EXISTS team_players_call_up;

CREATE TABLE team_players_call_up
(
    id        SERIAL PRIMARY KEY,
    match_id  INTEGER NOT NULL REFERENCES matches (id),
    team_id   INTEGER NOT NULL REFERENCES teams (id),
    player_id INTEGER NOT NULL REFERENCES players (id)
);


-- inserts
INSERT INTO continents (name) VALUES ('Europa');

INSERT INTO countries (name, key, continent_id) VALUES ('España', 'ESP', 1);
INSERT INTO countries (name, key, continent_id) VALUES ('Inglaterra', 'ENG', 1);

INSERT INTO competitions (name, type, continent_id, country_id) VALUES ('La Liga', 'league', 1, 1);
INSERT INTO competitions (name, type, continent_id, country_id) VALUES ('Premier League', 'league', 1, 2);
INSERT INTO competitions (name, type, continent_id, country_id) VALUES ('Champions League', 'tournament', 1, NULL);

INSERT INTO teams (name, country_id) VALUES ('Real Madrid CF', 1);
INSERT INTO teams (name, country_id) VALUES ('FC Barcelona', 1);
INSERT INTO teams (name, country_id) VALUES ('Atletico Madrid', 1);

INSERT INTO team_competitions (team_id, competition_id) VALUES (1, 1);
INSERT INTO team_competitions (team_id, competition_id) VALUES (2, 1);
INSERT INTO team_competitions (team_id, competition_id) VALUES (3, 1);


INSERT INTO teams (name, country_id) VALUES ('Manchester United', 2);
INSERT INTO teams (name, country_id) VALUES ('Arsenal', 2);
INSERT INTO teams (name, country_id) VALUES ('Chelsea', 2);
INSERT INTO teams (name, country_id) VALUES ('Everton', 2);
INSERT INTO teams (name, country_id) VALUES ('Tottenham', 2);
INSERT INTO teams (name, country_id) VALUES ('Manchester City', 2);

INSERT INTO team_competitions (team_id, competition_id) VALUES (4, 2);
INSERT INTO team_competitions (team_id, competition_id) VALUES (5, 2);
INSERT INTO team_competitions (team_id, competition_id) VALUES (6, 2);
INSERT INTO team_competitions (team_id, competition_id) VALUES (7, 2);
INSERT INTO team_competitions (team_id, competition_id) VALUES (8, 2);
INSERT INTO team_competitions (team_id, competition_id) VALUES (9, 2);
