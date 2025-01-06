CREATE TABLE IF NOT EXISTS tb_doctor (
    doctor_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    speciality VARCHAR(100) NOT NULL,
    gender CHAR(1) NOT NULL,
    crm VARCHAR(20) NOT NULL,
    description TEXT
);
