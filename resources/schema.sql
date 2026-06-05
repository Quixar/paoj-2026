DROP TABLE IF EXISTS grade;
DROP TABLE IF EXISTS absence;
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS student;
DROP TABLE IF EXISTS profesor;

CREATE TABLE profesor (
                          email VARCHAR(100) PRIMARY KEY,
                          name VARCHAR(50) NOT NULL,
                          surname VARCHAR(50) NOT NULL,
                          department VARCHAR(100) NOT NULL,
                          is_diriginte BOOLEAN DEFAULT FALSE,
                          group_name VARCHAR(50) DEFAULT NULL
);

CREATE TABLE student (
                         cod_inmatriculare VARCHAR(50) PRIMARY KEY,
                         name VARCHAR(50) NOT NULL,
                         surname VARCHAR(50) NOT NULL,
                         email VARCHAR(100) UNIQUE NOT NULL,
                         year INT NOT NULL,
                         group_name VARCHAR(50) DEFAULT NULL
);

CREATE TABLE subject (
                         code VARCHAR(50) PRIMARY KEY,
                         name VARCHAR(100) NOT NULL,
                         professor_email VARCHAR(100),
                         FOREIGN KEY (professor_email) REFERENCES profesor(email) ON DELETE SET NULL
);

CREATE TABLE grade (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       student_cod VARCHAR(50),
                       subject_code VARCHAR(50),
                       value INT NOT NULL,
                       date DATE NOT NULL,
                       FOREIGN KEY (student_cod) REFERENCES student(cod_inmatriculare) ON DELETE CASCADE,
                       FOREIGN KEY (subject_code) REFERENCES subject(code) ON DELETE CASCADE
);

CREATE TABLE absence (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         student_cod VARCHAR(50),
                         subject_code VARCHAR(50),
                         date DATE NOT NULL,
                         motivated BOOLEAN NOT NULL,
                         FOREIGN KEY (student_cod) REFERENCES student(cod_inmatriculare) ON DELETE CASCADE,
                         FOREIGN KEY (subject_code) REFERENCES subject(code) ON DELETE CASCADE
);