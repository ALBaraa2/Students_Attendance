CREATE TABLE courses (
  course_id varchar(10) PRIMARY KEY,
  instructor_name VARCHAR(25),
  course_name VARCHAR(50),
  course_location VARCHAR(5)
  CHECK (course_id ~ '^[A-Za-z]{4}\d{4}$')
);

CREATE TABLE users (
  id SERIAL,
  username VARCHAR(25) NOT NULL,
  email VARCHAR(25) unique NOT NULL,
  password VARCHAR(255) NOT NULL,
  user_type VARCHAR(15) NOT null,
  PRIMARY KEY (id),
  CHECK (user_type in ('teach assistant', 'admin')),
  CHECK (email LIKE '%@%.com'),
  CHECK (LENGTH(password) >= 6)
);

CREATE TYPE address AS (
  street VARCHAR(50),
  city VARCHAR(50)
);

CREATE TABLE students (
  student_id VARCHAR(9) PRIMARY KEY,
  student_name VARCHAR(50) NOT NULL,
  student_address address NOT NULL,
  gender VARCHAR(6) NOT NULL,
  CHECK (gender IN ('female', 'male')),
  CHECK (student_id ~ '^\d{9}$')
);

CREATE TABLE phone (
  student_id VARCHAR(9),
  student_phone VARCHAR(10) NOT NULL,
  PRIMARY KEY (student_id, student_phone),
  FOREIGN KEY (student_id) REFERENCES students (student_id) ON DELETE CASCADE
);

CREATE TABLE teach_assistant (
  id SERIAL,
  name VARCHAR(25),
  PRIMARY KEY (id),
  FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE section (
  course_id varchar(10) NOT NULL,
  year numeric(4) NOT NULL,
  semester VARCHAR(7) NOT NULL,
  sec_id numeric(3) NOT NULL,
  course_location VARCHAR(5),
  PRIMARY KEY (course_id, year, semester, sec_id),
  FOREIGN KEY (course_id) REFERENCES courses (course_id) ON DELETE cascade,
  check (semester in ('Fall', 'Winter', 'Spring', 'Summer')),
  CHECK (year >= 2000)
);

CREATE TABLE lectures (
  lecture_id varchar(2) NOT NULL,
  lecture_title VARCHAR(40) NOT NULL,
  lecture_date DATE NOT NULL,
  lecture_time TIME NOT NULL,
  lecture_location VARCHAR(5) NOT NULL,
  course_id varchar(10) NOT null,
  year numeric(4) NOT NULL,
  semester VARCHAR(7) NOT NULL,
  sec_id numeric(3) NOT NULL,
  PRIMARY KEY (lecture_id, course_id, year, semester, sec_id),
  FOREIGN KEY (course_id, year, semester, sec_id) REFERENCES section (course_id, year, semester, sec_id) on delete cascade
);

CREATE TABLE attendance (
lecture_id varchar(2) NOT NULL,
  course_id varchar(10) NOT NULL,
  year numeric(4) NOT NULL,
  semester varchar(7) NOT NULL,
  sec_id numeric(3) NOT NULL,
  student_id varchar(9) NOT NULL,
  attendance_status varchar(3) NOT NULL,
  PRIMARY KEY (lecture_id, course_id, year, semester, sec_id, student_id),
  FOREIGN KEY (lecture_id, course_id, year, semester, sec_id) REFERENCES lectures (lecture_id, course_id, year, semester, sec_id) ON DELETE CASCADE,
  FOREIGN KEY (student_id) REFERENCES students (student_id),
  check (attendance_status in ('yes', 'no'))
);

CREATE TABLE assist (
  course_id varchar(10) NOT NULL,
  year numeric(4) NOT NULL,
  semester varchar(7) NOT NULL,
  assistant_id serial NOT NULL,  
  sec_id numeric(3) NOT NULL,
  PRIMARY KEY (course_id, year, semester,assistant_id,sec_id),
  FOREIGN KEY (course_id, year, semester,sec_id) REFERENCES section (course_id, year, semester,sec_id) ON DELETE CASCADE,
  FOREIGN KEY (assistant_id) REFERENCES teach_assistant (id),
  UNIQUE (course_id, year, semester,sec_id),
  check (semester in ('Fall', 'Winter', 'Spring', 'Summer'))
);

CREATE TABLE enrollments (
  student_id VARCHAR(9) NOT NULL,
  course_id varchar(10) NOT NULL,
  year numeric(4) NOT NULL,
  semester varchar(7) NOT NULL,
  sec_id numeric(3) NOT NULL,
  PRIMARY KEY (student_id, course_id, year, semester, sec_id),
  FOREIGN KEY (student_id) REFERENCES students (student_id),
  FOREIGN KEY (course_id, year, semester, sec_id) REFERENCES section (course_id, year, semester, sec_id) ON DELETE CASCADE
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


CREATE OR REPLACE FUNCTION enroll_students_on_lecture_creation()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO attendance (lecture_id, course_id, year, semester, sec_id, student_id, attendance_status)
  SELECT NEW.lecture_id, NEW.course_id, NEW.year, NEW.semester, NEW.sec_id, enrollments.student_id, 'no'
  FROM enrollments
  WHERE enrollments.course_id = NEW.course_id
    AND enrollments.year = NEW.year
    AND enrollments.semester = NEW.semester
    AND enrollments.sec_id = NEW.sec_id;
    
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER lecture_creation_trigger
AFTER INSERT ON lectures
FOR EACH ROW
EXECUTE FUNCTION enroll_students_on_lecture_creation();


CREATE OR REPLACE FUNCTION add_student_to_attendance()
RETURNS TRIGGER AS $$
BEGIN
  INSERT INTO attendance (lecture_id, course_id, year, semester, sec_id, student_id, attendance_status)
  SELECT l.lecture_id, l.course_id, l.year, l.semester, l.sec_id, NEW.student_id, 'no'
  FROM lectures l
  WHERE l.course_id = NEW.course_id
    AND l.year = NEW.year
    AND l.semester = NEW.semester
    AND l.sec_id = NEW.sec_id;
    
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER student_course_link_trigger
AFTER INSERT ON enrollments
FOR EACH ROW
EXECUTE FUNCTION add_student_to_attendance();


CREATE FUNCTION check_section_capacity() RETURNS TRIGGER AS $$
BEGIN
  IF (
    SELECT COUNT(*) FROM enrollments
    WHERE course_id = NEW.course_id
    AND year = NEW.year
    AND semester = NEW.semester
    AND sec_id = NEW.sec_id
  ) >= 60 THEN
    INSERT INTO section (course_id, year, semester, sec_id, course_location)
    SELECT NEW.course_id, NEW.year, NEW.semester, MAX(sec_id) + 1, 'New Location'
    FROM section
    WHERE course_id = NEW.course_id
    AND year = NEW.year
    AND semester = NEW.semester
    GROUP BY course_id, year, semester;
    
    RETURN NEW;
  ELSE
    RETURN NEW;
  END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER section_capacity_trigger
BEFORE INSERT ON section
FOR EACH ROW
EXECUTE FUNCTION check_section_capacity();


CREATE FUNCTION generate_next_lecture_id(course_id varchar(10), year numeric(4), semester varchar(7), sec_id numeric(3))
  RETURNS varchar(2) AS
$$
DECLARE
  next_lecture_id varchar(2);
BEGIN
  SELECT COALESCE(MAX(lectures.lecture_id)::int, 0) + 1 INTO next_lecture_id
FROM lectures
WHERE lectures.course_id = generate_next_lecture_id.course_id
  AND lectures.year = generate_next_lecture_id.year
  AND lectures.semester = generate_next_lecture_id.semester
  AND lectures.sec_id = generate_next_lecture_id.sec_id;
 
  RETURN LPAD(next_lecture_id::varchar, 2, '0');
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION check_email_format(email_input VARCHAR)
RETURNS BOOLEAN AS $$
BEGIN
  IF email_input ~ '@.*\.com$' THEN
    RETURN TRUE;
  ELSE
    RETURN FALSE;
  END IF;
END;
$$ LANGUAGE plpgsql;


CREATE OR REPLACE FUNCTION auto_increment_sec_id()
RETURNS TRIGGER AS $$
DECLARE
  max_sec_id numeric(3);
BEGIN
  SELECT MAX(sec_id) INTO max_sec_id FROM section
  WHERE course_id = NEW.course_id
    AND year = NEW.year
    AND semester = NEW.semester;
  
  NEW.sec_id := COALESCE(max_sec_id, 0) + 1;
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER section_auto_increment_trigger
BEFORE INSERT ON section
FOR EACH ROW
EXECUTE FUNCTION auto_increment_sec_id();

INSERT INTO users (username, email, password, user_type) 
VALUES 
  ('admin', 'admin@gmail.com', '$2a$10$Hm0K6/9J9TCy.ZN0ZiIavupEHnIvUuyij.rELXav7EvI0hyUMeruG', 'admin'),
 ('assistant', 'user1@gmail.com', '$2a$10$Hm0K6/9J9TCy.ZN0ZiIavupEHnIvUuyij.rELXav7EvI0hyUMeruG', 'teach assistant');
