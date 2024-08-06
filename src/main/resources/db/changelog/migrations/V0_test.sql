-- liquibase formatted sql

-- changeset lpamintuan:0.1

CREATE TABLE testtable (
    id BIGSERIAL NOT NULL
);

-- rollback DROP TABLE IF EXISTS testtable