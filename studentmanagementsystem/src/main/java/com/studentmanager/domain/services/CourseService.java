package com.studentmanager.domain.services;

import com.studentmanager.domain.exceptions.io.EmptyStorageException;
import com.studentmanager.domain.exceptions.io.EntityNotFoundException;
import com.studentmanager.domain.exceptions.runtime.CourseTeacherAlreadyPresentException;
import com.studentmanager.domain.exceptions.runtime.GradeOutOfBoundsException;
import com.studentmanager.domain.models.Course;
import com.studentmanager.domain.models.Enrollment;
import com.studentmanager.domain.models.Student;
import com.studentmanager.domain.models.Teacher;
import com.studentmanager.domain.repositories.Repository;
import com.studentmanager.domain.repositories.RepositoryFactory;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * A service, containing all the course-related operations.
 */
public class CourseService extends CrudService<Course, Long> {
    private Repository<Enrollment, Long> enrollmentRepository;
    private Repository<Student, Long> studentRepository;
    private Repository<Teacher, Long> teacherRepository;

    public CourseService(Repository<Course, Long> courseRepository,
                          Repository<Student, Long> studentRepository,
                          Repository<Teacher, Long> teacherRepository,
                          Repository<Enrollment, Long> enrollmentRepository) {
        super(courseRepository);
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    /**
     * A static factory method, used for instantiation.
     *
     * @return the new instance
     */
    public static CourseService create() {
        RepositoryFactory repositoryFactory = RepositoryFactory.getInstance();
        return new CourseService(
                repositoryFactory.getCourseRepository(),
                repositoryFactory.getStudentRepository(),
                repositoryFactory.getTeacherRepository(),
                repositoryFactory.getEnrollmentRepository());
    }

    /**
     * Enrolls a student in a course.
     *
     * @param studentId - Id of the student.
     * @param courseId  - Id of the course.
     * @throws EntityNotFoundException when the course/student with this id is not found.
     */
    public void addStudentToCourse(Long studentId, Long courseId)
            throws EntityNotFoundException {
        Course course = this.findById(courseId);
        Student student = this.studentRepository.findById(studentId);
        Enrollment enrollment = new Enrollment(course, student);
        course.getEnrolledStudentsWithGrades().add(enrollment);
        this.update(course);
        this.enrollmentRepository.save(enrollment);
    }

    /**
     * Adds a teacher to a course.
     *
     * @param teacherId - Id of the teacher.
     * @param courseId  - Id of the course.
     * @throws EntityNotFoundException              when the course/student with this id is not found.
     * @throws CourseTeacherAlreadyPresentException when the course already has a teacher.
     */
    public void addTeacherToCourse(Long teacherId, Long courseId)
            throws EntityNotFoundException, CourseTeacherAlreadyPresentException {
        Course course = this.findById(courseId);
        verifyCourseHasNoTeacher(course);
        Teacher teacher = this.teacherRepository.findById(teacherId);
        course.setTeacher(teacher);
        super.repository.update(course);
    }

    private void verifyCourseHasNoTeacher(Course course)
            throws CourseTeacherAlreadyPresentException {
        if (course.getTeacher() != null) {
            throw new CourseTeacherAlreadyPresentException(
                    String.format("Course: %s already has a teacher",
                            course.getName())
            );
        }
    }

    /**
     * Adds a grade to a student in a specific course.
     * If grade is outside the 2-6 range, an exception is thrown.
     *
     * @param grade     - The grade to be added.
     * @param studentId - Id of the student.
     * @param courseId  - Id of the course.
     * @throws EntityNotFoundException when the course/student with this id is not found.
     * @throws EmptyStorageException   when there are no enrollments for the specific course.
     */
    public void addGradeToStudentInCourse(double grade, Long studentId, Long courseId)
            throws EntityNotFoundException, EmptyStorageException {
        if (grade < 2 || grade > 6) {
            throw new GradeOutOfBoundsException("Grade should be in the range %f-%f!");
        }
        Enrollment enrollment = this.getEnrollmentByStudentAndCourse(studentId, courseId);
        enrollment.getGrades().add(grade);
        this.enrollmentRepository.update(enrollment);
    }

    private Enrollment getEnrollmentByStudentAndCourse(Long studentId, Long courseId)
            throws EntityNotFoundException, EmptyStorageException {
        return this.enrollmentRepository.findAll()
                .filter(e -> e.getStudent().getId().equals(studentId) &&
                        e.getCourse().getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No student with id %d is enrolled in course with id %d.",
                                studentId, courseId)
                ));
    }

    public double getStudentAverageGradeForCourse(Long studentId, Long courseId)
            throws EntityNotFoundException, EmptyStorageException {
        return getEnrollmentAverageGrade(
                this.getEnrollmentByStudentAndCourse(studentId, courseId));
    }

    private double getEnrollmentAverageGrade(Enrollment enrollment) {
        return enrollment.getGrades().stream()
                .mapToDouble(Double::valueOf)
                .average()
                .orElse(0);
    }

    /**
     * Calculates the overall average grade for a specific student by his id.
     *
     * @param studentId - Id of the student.
     * @return The average grade for this student over all his enrollments.
     * @throws EmptyStorageException when the student doesn't have any course enrollments.
     */
    public double getStudentOverallAverageGrade(Long studentId)
            throws EmptyStorageException {
        return this.getStudentEnrollments(studentId).stream()
                .filter(enrollment -> enrollment.getStudent().getId().equals(studentId))
                .mapToDouble(this::getEnrollmentAverageGrade)
                .average()
                .orElse(0);
    }

    private Set<Enrollment> getStudentEnrollments(Long studentId)
            throws EmptyStorageException {
        return this.enrollmentRepository.findAll()
                .filter(enrollment -> enrollment.getStudent().getId().equals(studentId))
                .collect(Collectors.toSet());
    }

    public double getAverageGradeForAllStudentsInCourse(Long courseId)
            throws EmptyStorageException, EntityNotFoundException {
        return this.getCourseEnrollments(courseId).stream()
                .mapToDouble(this::getEnrollmentAverageGrade)
                .average()
                .orElse(0);
    }

    private Set<Enrollment> getCourseEnrollments(Long courseId)
            throws EmptyStorageException, EntityNotFoundException {
        Set<Enrollment> courseEnrollments = this.enrollmentRepository.findAll()
                .filter(enrollment -> enrollment.getCourse().getId().equals(courseId))
                .collect(Collectors.toSet());
        if (courseEnrollments.size() == 0) {
            throw new EntityNotFoundException(
                    String.format("No course with id %d is present.",
                            courseId)
            );
        }
        return courseEnrollments;
    }

    public String showAllStudentsGroupedByCourseAndAverageGrade() throws EmptyStorageException {
        return getAllStudentsGroupedByCourseAndAverageGrade().toString();
    }

    private Set<Student> getAllStudentsGroupedByCourseAndAverageGrade() throws EmptyStorageException {
        return this.enrollmentRepository.findAll()
                .sorted((c1, c2) -> (int) Math.round(
                        this.getEnrollmentAverageGrade(c1) - this.getEnrollmentAverageGrade(c2)))
                .map(Enrollment::getStudent)
                .collect(Collectors.toSet());
    }

    /**
     * Shows information about the participants in every course.
     *
     * @return A String, containing the formatted information.
     * @throws EmptyStorageException when there are no courses/enrollments present.
     */
    public String showAllCoursesWithTeachersAndStudents()
            throws EmptyStorageException {
        StringBuilder sb = new StringBuilder();
        Set<Course> courses = this.findAll()
                .collect(Collectors.toSet());
        for (Course course : courses) {
            Teacher teacher = course.getTeacher();
            Set<Student> students = course.getEnrolledStudentsWithGrades().stream()
                    .map(Enrollment::getStudent)
                    .collect(Collectors.toSet());
            sb.append(String.format("Course: %s%n\tTeacher: %s%n",
                    course.getName(),
                    teacher.getName()))
                    .append(String.format("\t\tStudents:%n"));
            for (Student student : students) {
                sb.append(String.format("\t\t\t%s%n",
                        student));
            }
        }
        return sb.toString();
    }
}
