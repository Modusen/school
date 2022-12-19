-- liquibase formatted sql

-- changeset dastrashenya:2
CREATE INDEX student_name_index ON student (name);

-- changeset dastrashenya:3
CREATE INDEX faculty_name_color_index ON faculty (name, color);




