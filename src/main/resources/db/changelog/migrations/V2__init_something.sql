-- liquibase formatted sql

-- changeset author:v2-init-something
-- tag: tag_v2
CREATE TABLE something (
                              id SERIAL PRIMARY KEY,
                              start_date TIMESTAMP NOT NULL,
                              end_date TIMESTAMP NOT NULL
);

-- rollback DROP TABLE something;