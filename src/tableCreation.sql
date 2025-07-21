CREATE TABLE IF NOT EXISTS stand_users(
    id SERIAL,
    first_name TEXT NOT NULL,
    last_name TEXT,
    stand_name TEXT,
    age INTEGER,
    from_part INTEGER
);