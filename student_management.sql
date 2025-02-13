USE student_management;
DROP TABLE IF EXISTS students;
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    gender VARCHAR(10),
    age INT,
    major VARCHAR(100),
    academicYear VARCHAR(20),
    hometown VARCHAR(50)
);
