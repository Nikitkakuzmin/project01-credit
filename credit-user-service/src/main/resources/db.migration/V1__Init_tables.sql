CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       credit_quantity INTEGER,
                       credit_repaid INTEGER,
                       credit_sum NUMERIC,
                       email VARCHAR(255),
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       password VARCHAR(255),
                       repaid_sum NUMERIC,
                       username VARCHAR(255),
                       rate DOUBLE
);
CREATE TABLE user_roles (
                            user_id BIGINT,
                            role VARCHAR(50) CHECK (role IN ('ADMIN')),
                            PRIMARY KEY (user_id, role),
                            FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
