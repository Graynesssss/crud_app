CREATE TABLE reservations (
                              id SERIAL PRIMARY KEY,
                              start_date TIMESTAMP NOT NULL,
                              end_date TIMESTAMP NOT NULL,
                              status VARCHAR(50) NOT NULL,
                              room_id BIGINT NOT NULL,
                              user_id BIGINT NOT NULL
);

-- DROP TABLE reservations;
-- DROP TABLE IF EXISTS flyway_schema_history CASCADE;
-- DROP TABLE event_publication;