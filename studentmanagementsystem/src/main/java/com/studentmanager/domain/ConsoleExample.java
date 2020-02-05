package com.studentmanager.domain;

import com.studentmanager.domain.enums.Degree;
import com.studentmanager.domain.exceptions.io.EmptyStorageException;
import com.studentmanager.domain.exceptions.io.EntityNotFoundException;
import com.studentmanager.domain.models.Course;
import com.studentmanager.domain.models.Student;
import com.studentmanager.domain.models.Teacher;
import com.studentmanager.domain.services.CourseService;
import com.studentmanager.domain.services.StudentService;
import com.studentmanager.domain.services.TeacherService;

/**
 * Used for fast and easy testing of the application's
 * main functionality, without the need to launch it
 * with a CLI.
 * It's NOT intended for the end user.
 */
public class ConsoleExample {
    public static void main(String[] args)
            throws EmptyStorageException, EntityNotFoundException {
        CourseService courseService = CourseService.create();
        StudentService studentService = StudentService.create();
        TeacherService teacherService = TeacherService.create();

        //add course functionality
        Course javaBasicsCourse = new Course("Java Basics", 200);
        Course javaDBCourse = new Course("Java DB", 120);
        Long courseId = courseService.save(javaBasicsCourse).getId();
        courseService.save(javaDBCourse);

        //add student functionality
        Student sPesho = new Student("StudentPesho", 20);
        Student sGosho = new Student("StudentGosho", 23);
        studentService.save(sPesho);
        studentService.save(sGosho);

        //add teacher functionality
        Teacher tPesho = new Teacher("TeacherPesho", 59, Degree.PHD);
        Teacher tGosho = new Teacher("TeacherGosho", 28, Degree.BSc);
        teacherService.save(tPesho);
        teacherService.save(tGosho);

        //add a teacher to a specific course
        courseService.addTeacherToCourse(tPesho.getId(), javaBasicsCourse.getId());
        courseService.addTeacherToCourse(tGosho.getId(), javaDBCourse.getId());

        //add a student to a specific course
        courseService.addStudentToCourse(sPesho.getId(), javaBasicsCourse.getId());
        courseService.addStudentToCourse(sGosho.getId(), javaBasicsCourse.getId());
        courseService.addStudentToCourse(sGosho.getId(), javaDBCourse.getId());


        //add a grade for student in a specific course
        courseService.addGradeToStudentInCourse(3, sPesho.getId(), javaBasicsCourse.getId());
        courseService.addGradeToStudentInCourse(4, sPesho.getId(), javaBasicsCourse.getId());
        courseService.addGradeToStudentInCourse(5, sGosho.getId(), javaBasicsCourse.getId());
        courseService.addGradeToStudentInCourse(6, sGosho.getId(), javaBasicsCourse.getId());
        courseService.addGradeToStudentInCourse(2, sGosho.getId(), javaDBCourse.getId());
        courseService.addGradeToStudentInCourse(5, sGosho.getId(), javaDBCourse.getId());

        //show all students grouped by course (alphabetically) and then by their average grade for the course (ascending).
        System.out.println(courseService.showAllStudentsGroupedByCourseAndAverageGrade());

        //show all courses and their teachers and students (without grades).
        System.out.println(courseService.showAllCoursesWithTeachersAndStudents());

        //get the average grade for all students in a specific course.
        System.out.println(courseService.getAverageGradeForAllStudentsInCourse(javaBasicsCourse.getId()));

        //get the overall average grade for student (between all of his courses using the average grade).
        System.out.println(courseService.getStudentOverallAverageGrade(sGosho.getId()));
        System.out.println(courseService.getStudentOverallAverageGrade(sPesho.getId()));
    }
}
