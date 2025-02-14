CREATE TABLE users (
    id INTEGER PRIMARY KEY NOT NULL,
    username VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL
);

-- user1 / 123
INSERT INTO users (username, password)
    VALUES ("user1", "$2a$10$YHoxL9fJHHFQ62odLgq3r.ciBukhJyse/7AXv7Cut47bQdsmrQgcG");

-- user1 / 123
INSERT INTO users (username, password)
    VALUES ("user2", "$2a$10$YHoxL9fJHHFQ62odLgq3r.ciBukhJyse/7AXv7Cut47bQdsmrQgcG");