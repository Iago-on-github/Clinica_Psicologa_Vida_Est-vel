CREATE TABLE IF NOT EXISTS tb_patient (
    patient_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT,
    gender CHAR(1) NOT NULL,
    description TEXT
);