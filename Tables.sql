CREATE TABLE courses (
  course_id varchar(10) PRIMARY KEY,
  instructor_name VARCHAR(25),
  course_name VARCHAR(50),
  course_location VARCHAR(5)
);

CREATE TABLE users (
  id SERIAL,
  username VARCHAR(25) NOT NULL,
  email VARCHAR(25) unique NOT NULL,
  password VARCHAR(25) NOT NULL,
  user_type VARCHAR(15) NOT null,
  PRIMARY KEY (id),
  CONSTRAINT user_type_check CHECK (user_type = 'teach assistant' or user_type = 'admin')
);

CREATE TABLE teach_assistant (
  id SERIAL,
  name VARCHAR(25),
  PRIMARY KEY (id),
  CONSTRAINT teach_assistant_users_fk FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE OR REPLACE FUNCTION add_to_teach_assistant() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.user_type = 'teach assistant' THEN
        INSERT INTO teach_assistant (id, name) VALUES (NEW.id, NEW.username);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER add_to_teach_assistant_trigger
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION add_to_teach_assistant();

CREATE TYPE address AS (
  city VARCHAR(10),
  street VARCHAR(15)
);

CREATE TABLE students (
  student_id varchar(9) PRIMARY KEY,
  student_name VARCHAR(50) NOT NULL,
  student_phone VARCHAR(10)[] NOT NULL,
  student_address address NOT NULL
);

CREATE TABLE enrollments (
  course_id varchar(10) NOT NULL,
  student_id varchar(9) NOT NULL,
  sec_id numeric(3) NOT NULL,
  semester VARCHAR(7) NOT NULL,
  year numeric(4) NOT NULL,
  PRIMARY KEY (course_id, student_id),
  CONSTRAINT enrollments_courses_fk FOREIGN KEY (course_id) REFERENCES courses (course_id) on delete cascade,
  constraint enrollments_student_fk FOREIGN KEY (student_id) REFERENCES students (student_id) on delete cascade,
  CONSTRAINT semester_check CHECK (semester = 'Spring' or semester = 'Fall' or semester = 'Winter' or semester = 'Summer')
);

CREATE TABLE lectures (
  lecture_id varchar(2) NOT NULL,
  lecture_title VARCHAR(40) NOT NULL,
  lecture_date DATE NOT NULL,
  lecture_time TIME NOT NULL,
  lecture_location VARCHAR(5) NOT NULL,
  course_id varchar(10) NOT null,
  PRIMARY KEY (lecture_id, course_id),
  FOREIGN KEY (course_id) REFERENCES courses(course_id) on delete cascade
);

CREATE TABLE attendance (
  course_id varchar(10) NOT null,
  lecture_id varchar(2) NOT NULL,
  student_id varchar(9) NOT NULL,
  attendance_status VARCHAR(3) NOT NULL,
  PRIMARY KEY (course_id, lecture_id, student_id),
  constraint attendance_lecture_fk FOREIGN KEY (lecture_id, course_id) REFERENCES lectures (lecture_id, course_id) on delete cascade,
  constraint attendance_student_fk FOREIGN KEY (student_id) REFERENCES students (student_id) on delete cascade,
  CONSTRAINT attendance_status_check CHECK (attendance_status = 'yes' OR attendance_status = 'no')
);

CREATE TABLE assist (
  id INTEGER,
  course_id varchar(10),
  semester VARCHAR(7),
  year numeric(4),
  sec_id numeric(3),
  PRIMARY KEY (id, course_id),
  constraint assist_id_fk FOREIGN KEY (id) REFERENCES teach_assistant (id) ON DELETE cascade,
  CONSTRAINT assist_courses_fk FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE cascade,
  CONSTRAINT semester_check CHECK (semester = 'Spring' or semester = 'Fall' or semester = 'Winter' or semester = 'Summer')
);


----------------------------------------------------------------



INSERT INTO courses (course_id, instructor_name, course_name, course_location)
VALUES
('CS101', 'John Smith', 'Introduction to Computer Science', 'B100'),
('MATH202', 'Jane Doe', 'Calculus II', 'M200'),
('PHYS101', 'Bob Johnson', 'Introduction to Physics', 'P100'),
('HIST101', 'Samantha Lee', 'World History', 'H200'),
('ENGL201', 'David Brown', 'Advanced English Writing', 'E300');

INSERT INTO users (username, email, password, user_type) VALUES
('baraa', 'baraa@gmail.com', '123', 'admin'),
('feras', 'feras@gmail.com', '123', 'admin'),
('hamza', 'hamza@gmail.com', '123', 'admin'),
('Jane Smith', 'janesmith@example.com', 'password456', 'teach assistant'),
('Bob Johnson', 'bobjohnson@example.com', 'password789', 'teach assistant'),
('Samantha Lee', 'samanthalee@example.com', 'passwordabc', 'teach assistant'),
('David Brown', 'davidbrown@example.com', 'passworddef', 'teach assistant');

INSERT INTO students (student_id, student_name, student_phone, student_address)
VALUES
('202300001', 'Ahmed Ali', ARRAY['555-1234', '555-5678'], ROW('Cairo', 'Main St.')),
('202300002', 'Fatma Omar', ARRAY['555-2345', '555-6789'], ROW('Giza', 'High St.')),
('202300003', 'Mohamed Hassan', ARRAY['555-3456', '555-7890'], ROW('Alexandria', 'Broadway')),
('202300004', 'Hala Ahmed', ARRAY['555-4567', '555-8901'], ROW('Cairo', 'First St.')),
('202300005', 'Khaled Emad', ARRAY['555-5678', '555-9012'], ROW('Alexandria', 'Third St.'));

INSERT INTO enrollments (course_id, student_id, sec_id, semester, year) VALUES
('CS101', '202300001', 101, 'Fall', 2022),
('MATH202', '202300002', 101, 'Spring', 2022),
('PHYS101', '202300003', 102, 'Fall', 2023),
('ENGL201', '202300004', 101, 'Summer', 2022),
('HIST101', '202300005', 103, 'Fall', 2022);

INSERT INTO lectures (lecture_id, lecture_title, lecture_date, lecture_time, lecture_location, course_id)
VALUES
('01', 'Introduction to Computer Science', '2023-05-11', '14:00:00', 'B100', 'CS101'),
('01', 'Calculus II Lecture 1', '2023-05-12', '15:30:00', 'M200', 'MATH202'),
('01', 'Introduction to Physics', '2023-05-13', '11:00:00', 'P100', 'PHYS101'),
('01', 'World History', '2023-05-14', '13:00:00', 'H200', 'HIST101'),
('01', 'Advanced English Writing', '2023-05-15', '10:00:00', 'E300', 'ENGL201'),
('02', 'Introduction to Computer Science II', '2023-05-13', '14:00:00', 'B100', 'CS101');

INSERT INTO attendance (course_id, lecture_id, student_id, attendance_status)
VALUES
('CS101', '01', '202300001', 'yes'),
('MATH202', '01', '202300002', 'no'),
('PHYS101', '01', '202300003', 'yes'),
('HIST101', '01', '202300004', 'yes'),
('ENGL201', '01', '202300005', 'no');

INSERT INTO assist (id, course_id, semester, year, sec_id) VALUES
(2, 'CS101', 'Spring', 2022, 1),
(3, 'MATH202', 'Fall', 2021, 2),
(4, 'PHYS101', 'Winter', 2022, 3),
(5, 'HIST101', 'Summer', 2021, 4),
(2, 'ENGL201', 'Fall', 2021, 2);