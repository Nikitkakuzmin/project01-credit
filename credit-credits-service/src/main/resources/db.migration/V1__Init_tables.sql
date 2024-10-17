CREATE TABLE credits (
                         id SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         max_amount INTEGER NOT NULL,
                         rate DOUBLE PRECISION NOT NULL
);
