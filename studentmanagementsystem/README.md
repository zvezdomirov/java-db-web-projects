# Student Management Application

## The domain includes:
- The domain models.
- Database layer, which defines interface through which the domain models can be
persisted in a data structure, file/s, or a real database.
- CRUD (create, read, update, delete) functionality in the form of repositories.

## The CLI:
- Handles user interaction via the command line interface.
- It serves as a presentation layer for the application, which means that no data is handled in the command processor itself.
- Implements services, which define the business logic in the application, which interacts with the Domain layer for persiting the data.

## Core business logic functionality of the Student Management Application
- add a new course.
- add a new student.
- add a new teacher.
- add a teacher to a specific course (max 1).
- add a student to a specific course.
- add a grade for student in a specific course. (grade 2.0-6.0)
- show all students grouped by course (alphabetically) and then by their average grade for the course
(ascending).
- show all courses and their teachers and students (without grades).
- show the average grade for all students in a specific course.
- show a total average grade for student (between all of his courses).

## Actors

### Teacher
- name
- degree (MSc, BSc, PHD)

### Student
- name
- age

### Course    
- name
- total hours
