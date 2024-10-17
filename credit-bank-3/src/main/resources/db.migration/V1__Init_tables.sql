CREATE TABLE bank3 (
                       id SERIAL PRIMARY KEY,
                       credit_quantity INTEGER,
                       credit_repaid INTEGER,
                       credit_sum NUMERIC,
                       email VARCHAR(255),
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       password VARCHAR(255),
                       repaid_sum NUMERIC,
                       username VARCHAR(255)
);
