-- liquibase formatted sql

-- changeset lpamintuan:1.0

CREATE TABLE user_account_details (

    id BIGSERIAL NOT NULL,
    username VARCHAR(255) NOT NULL,
    password TEXT NOT NULL,

    CONSTRAINT user_account_details_pk PRIMARY KEY (id),
    CONSTRAINT user_account_details_username_unq UNIQUE (username)
);

-- rollback DROP TABLE IF EXISTS user_account_details

-- changeset lpamintuan:1.1
-- comment insert an admin user

INSERT INTO user_account_details (username, password) VALUES ('admin', '{noop}admin');

-- rollback DELETE FROM user_account_details WHERE username = 'admin'