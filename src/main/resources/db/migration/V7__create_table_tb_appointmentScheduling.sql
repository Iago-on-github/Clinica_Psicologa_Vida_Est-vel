CREATE TABLE IF NOT EXISTS tb_appointmentScheduling (
    scheduling_id SERIAL PRIMARY KEY,
    doctor_id INT NOT NULL REFERENCES tb_doctor(doctor_id),
    patient_id INT NOT NULL REFERENCES tb_patient(patient_id),
    scheduledTimeFor TIMESTAMP
);
