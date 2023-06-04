INSERT INTO courses (course_id, instructor_name, course_name, course_location)
VALUES
  ('COMP1234', 'John Doe', 'Introduction to Computer Science', 'A123'),
  ('MATH5678', 'Jane Smith', 'Advanced Mathematics', 'B456'),
  ('PHYS9876', 'David Johnson', 'Physics for Engineers', 'C789'),
  ('CHEM4321', 'Sarah Thompson', 'Organic Chemistry', 'D012'),
  ('BIOL6543', 'Michael Davis', 'Introduction to Biology', 'E345');
  
INSERT INTO students (student_id, student_name, student_address, gender)
VALUES
  ('123456789', 'John Doe', ('123 Main St', 'New York'), 'male'),
  ('987654321', 'Jane Smith', ('456 Elm St', 'Los Angeles'), 'female'),
  ('567890123', 'Bob Johnson', ('789 Oak St', 'Chicago'), 'male'),
  ('456789012', 'Alice Williams', ('321 Pine St', 'San Francisco'), 'female'),
  ('234567890', 'Mike Davis', ('987 Maple St', 'Seattle'), 'male'),
 ('456123789', 'Hamza', ('987 Maple St', 'Seattle'), 'male'),
('789123456', 'Hadi', ('987 Maple St', 'Seattle'), 'male'),
('654987321', 'Shadi', ('987 Maple St', 'Seattle'), 'male');

INSERT INTO phone (student_id, student_phone)
VALUES
  ('123456789', '1234567890'),
  ('987654321', '9876543210'),
  ('567890123', '5678901234'),
  ('456789012', '4567890123'),
  ('456123789', '5961489647'),
  ('234567890', '2345678901'),
 ('789123456', '7426589955'),
('654987321', '2103670169');
 
INSERT INTO section (course_id, year, semester, course_location)
VALUES
  ('COMP1234', 2022, 'Fall', 'A123'),
  ('COMP1234', 2022, 'Fall', 'A123'),
  ('COMP1234', 2022, 'Fall', 'A123'),
  ('MATH5678', 2022, 'Spring', 'B456'),
  ('PHYS9876', 2023, 'Winter', 'C789'),
  ('CHEM4321', 2023, 'Summer', 'D012'),
  ('CHEM4321', 2023, 'Summer', 'D012'),
  ('BIOL6543', 2022, 'Fall', 'E345'),
 ('BIOL6543', 2022, 'Fall', 'E345');

 
INSERT INTO enrollments (student_id, course_id, year, semester, sec_id)
VALUES
  ('123456789', 'COMP1234', 2022, 'Fall', 1),
  ('987654321', 'COMP1234', 2022, 'Fall', 1),
  ('987654321', 'MATH5678', 2022, 'Spring', 1),
  ('456123789', 'PHYS9876', 2023, 'Winter', 1),
  ('789123456', 'CHEM4321', 2023, 'Summer', 1),
  ('456123789', 'CHEM4321', 2023, 'Summer', 1),
  ('654987321', 'BIOL6543', 2022, 'Fall', 1),
 ('789123456', 'BIOL6543', 2022, 'Fall', 2);

INSERT INTO lectures (lecture_id, lecture_title, lecture_date, lecture_time, lecture_location, course_id, year, semester, sec_id)
VALUES
  (generate_next_lecture_id('COMP1234', 2022, 'Fall', 1), 'Introduction to Computer Science', '2022-09-01', '09:00:00', 'A123', 'COMP1234', 2022, 'Fall', 1),
  (generate_next_lecture_id('MATH5678', 2022, 'Spring', 1), 'Calculus I', '2022-02-15', '14:30:00', 'B456', 'MATH5678', 2022, 'Spring', 1),
  (generate_next_lecture_id('PHYS9876', 2023, 'Winter', 1), 'Physics Mechanics', '2023-01-10', '10:15:00', 'C789', 'PHYS9876', 2023, 'Winter', 1),
  (generate_next_lecture_id('CHEM4321', 2023, 'Summer', 1), 'Organic Chemistry', '2023-07-05', '13:45:00', 'D012', 'CHEM4321', 2023, 'Summer', 1),
  (generate_next_lecture_id('CHEM4321', 2023, 'Summer', 1), 'Organic Chemistry I', '2023-07-07', '13:45:00', 'D012', 'CHEM4321', 2023, 'Summer', 1),
  (generate_next_lecture_id('BIOL6543', 2022, 'Fall', 1), 'Introduction to Biology', '2022-09-15', '11:30:00', 'E345', 'BIOL6543', 2022, 'Fall', 1),
 (generate_next_lecture_id('BIOL6543', 2022, 'Fall', 2), 'Introduction to Biology', '2022-09-15', '12:30:00', 'E345', 'BIOL6543', 2022, 'Fall', 2),
(generate_next_lecture_id('BIOL6543', 2022, 'Fall', 2), 'Introduction to Biology I', '2022-09-18', '11:30:00', 'E345', 'BIOL6543', 2022, 'Fall', 2);
