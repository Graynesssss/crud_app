-- liquibase formatted sql

-- changeset author:v1-init-reservations
-- tag: tag_v1
CREATE TABLE reservations (
                              id SERIAL PRIMARY KEY,
                              start_date TIMESTAMP NOT NULL,
                              end_date TIMESTAMP NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              room_id BIGINT NOT NULL,
                              user_id BIGINT NOT NULL
);

-- rollback DROP TABLE reservations;