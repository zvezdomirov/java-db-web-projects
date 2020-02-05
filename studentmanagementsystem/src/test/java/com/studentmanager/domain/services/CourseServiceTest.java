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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//TODO: Refactor DataProviders' names
@Test
public class CourseServiceTest {
    private static final Long VALID_ID = 0L;
    private static final String VALID_COURSE_NAME = "Java";
    private static final String VALID_STUDENT_NAME = "sPesho";
    private static final String VALID_TEACHER_NAME = "tGosho";
    private static final int VALID_COURSE_HOURS = 120;
    private static final double VALID_GRADE = 6;

    @Mock
    private Repository<Course, Long> mockCourseRepo;

    @Mock
    private Repository<Student, Long> mockStudentRepo;

    @Mock
    private Repository<Teacher, Long> mockTeacherRepo;

    @Mock
    private Repository<Enrollment, Long> mockEnrollmentRepo;

    @InjectMocks
    private CourseService courseService;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        this.courseService = new CourseService(
                mockCourseRepo,
                mockStudentRepo,
                mockTeacherRepo,
                mockEnrollmentRepo
        );
    }

    @Test
    public void addStudentToCourse_CorrectParametersGiven_ShouldUpdateCourseStudents()
            throws EntityNotFoundException {
        // Arrange:
        Course testCourse = new Course(VALID_COURSE_NAME, VALID_COURSE_HOURS);
        Student testStudent = new Student(VALID_STUDENT_NAME, 20);
        when(this.mockCourseRepo.update(testCourse))
                .thenReturn(testCourse);
        when(this.mockCourseRepo.findById(anyLong()))
                .thenReturn(testCourse);
        when(this.mockStudentRepo.findById(anyLong()))
                .thenReturn(testStudent);

        // Act:
        int enrollmentsCount = testCourse.getEnrolledStudentsWithGrades().size();
        this.courseService.addStudentToCourse(VALID_ID, VALID_ID);

        // Assert:
        Assert.assertEquals(testCourse.getEnrolledStudentsWithGrades().size(), enrollmentsCount + 1);
    }

    @Test(expectedExceptions = CourseTeacherAlreadyPresentException.class)
    public void addTeacherToCourse_AddingMoreThanOneTeacher_ShouldThrowException() throws EntityNotFoundException {
        // Arrange:
        Course mockCourse = mock(Course.class);
        Teacher mockTeacher = mock(Teacher.class);
        when(this.mockCourseRepo.findById(anyLong()))
                .thenReturn(mockCourse);
        when(mockCourse.getTeacher())
                .thenReturn(mockTeacher);

        // Act:
        this.courseService.addTeacherToCourse(VALID_ID, VALID_ID);
    }

    @Test(expectedExceptions = GradeOutOfBoundsException.class,
            dataProvider = "invalidGradesProvider")
    @Parameters({"invalidGrade"})
    public void addGradeToStudentInCourse_InvalidGradeGiven_ShouldThrowException(double invalidGrade) throws EmptyStorageException, EntityNotFoundException {
        this.courseService.addGradeToStudentInCourse(
                invalidGrade,
                VALID_ID,
                VALID_ID);
    }

    @DataProvider(name = "invalidGradesProvider")
    public Object[][] invalidGradesProvider() {
        return new Object[][]{
                {1.5},
                {6.5},
                {-1}
        };
    }

//    @Test
//    public void addGradeToStudentInCourse_ValidParametersGiven_ShouldExecuteAllStatementsAfterIfBlock() throws EntityNotFoundException, EmptyStorageException {
//        // Arrange:
//        Course mockCourse = mock(Course.class);
//        Student mockStudent = mock(Student.class);
//        List<Double> studentGradesBeforeAct = new ArrayList<>();
//        when(this.mockCourseRepo.findById(VALID_ID))
//                .thenReturn(mockCourse);
//        when(this.mockStudentRepo.findById(VALID_ID))
//                .thenReturn(mockStudent);
//        when(this.mockCourseRepo.update(mockCourse))
//                .thenReturn(mockCourse);
//        // Act:
//        this.courseService.addGradeToStudentInCourse(VALID_GRADE, VALID_ID, VALID_ID);
//
//        // Assert:
//        //It should execute all statements
//    }
//
//    @Test(dataProvider = "gradesWithExpectedAverage")
//    @Parameters({"validGrades", "expectedAverage"})
//    public void getAverageGradeForStudentInCourse_ShouldReturnCorrectAverage(
//            List<Double> validGrades, double expectedAverage) throws EntityNotFoundException, EmptyStorageException {
//        // Arrange:
//        Course mockCourse = mock(Course.class);
//        Student mockStudent = mock(Student.class);
//        when(this.mockStudentRepo.findById(VALID_ID))
//                .thenReturn(mockStudent);
//        when(this.mockCourseRepo.findById(VALID_ID))
//                .thenReturn(mockCourse);
//        // Act:
//        double actualAverage = this.courseService.getStudentAverageGradeForCourse(
//                VALID_ID,
//                VALID_ID);
//        Assert.assertEquals(actualAverage, expectedAverage);
//    }
//
//    @DataProvider(name = "gradesWithExpectedAverage")
//    public Object[][] validGradesWithExpectedAverageProvider() {
//        return new Object[][]{
//                {Arrays.asList(4.0, 5.0), 4.5},
//                {Arrays.asList(4.0, 5.0, 6.0), 5.0},
//                {Arrays.asList(2.0, 3.0, 3.0, 6.0), 3.5}
//        };
//    }

//    @Test(dataProvider = "studentWithCoursesValidData")
//    @Parameters({"student", "studentCourses"})
//    public void getOverallAverageGradeForStudent_ShouldWorkCorrectly
//            (Student student, Set<Course> studentCourses)
//            throws EmptyStorageException, CourseNotFoundException, StudentNotFoundException {
//        CourseService spyCourseService = Mockito.spy(this.courseService);
//        // Arrange:
//        when(this.mockStudentRepo.findById(VALID_ID))
//                .thenReturn(student);
//        when(this.mockCourseRepo.findAll())
//                .thenReturn(studentCourses);
//        Mockito.doReturn(VALID_GRADE)
//                .when(spyCourseService).getStudentAverageGradeForCourse(anyLong(), anyLong());
//        // Act:
//        double actualResult = spyCourseService.getStudentOverallAverageGrade(VALID_ID);
//
//        //Assert:
//        Assert.assertEquals(VALID_GRADE, actualResult);
//    }
//
//    @DataProvider(name = "studentWithCoursesValidData")
//    public Object[][] getOverallAverageGradeForStudentDataProvider() {
//        Student testStudent = new Student(VALID_STUDENT_NAME, 20);
//        Set<Course> testCourses = new TreeSet<>();
//        // Add 5 courses, enroll the student only in the ones with even index
//        for (int i = 0; i < 5; i++) {
//            Course currentCourse = new Course(VALID_COURSE_NAME + i, 10 * i);
//            if (i % 2 == 0) {
//                currentCourse.enrollStudent(testStudent);
//            }
//            testCourses.add(currentCourse);
//        }
//        return new Object[][]{
//                {testStudent, testCourses}
//        };
//    }
//
//    @Test(dataProvider = "courseWithStudentsValidData")
//    @Parameters({"course", "courseStudents"})
//    public void getAverageGradeForAllStudentsInCourse_ShouldWorkCorrectly
//            (Course course, Set<Student> courseStudents)
//            throws EmptyStorageException, CourseNotFoundException, StudentNotFoundException {
//        CourseService spyCourseService = Mockito.spy(this.courseService);
//        // Arrange:
//        when(this.mockCourseRepo.findById(VALID_ID))
//                .thenReturn(course);
//        Mockito.doReturn(VALID_GRADE)
//                .when(spyCourseService).getStudentAverageGradeForCourse(anyLong(), anyLong());
//        // Act:
//        double actualResult = spyCourseService.getAverageGradeForAllStudentsInCourse(VALID_ID);
//
//        //Assert:
//        Assert.assertEquals(VALID_GRADE, actualResult);
//    }
//
//    @DataProvider(name = "courseWithStudentsValidData")
//    public Object[][] getAverageGradeForAllStudentsInCourseDataProvider() {
//        Course testCourse = new Course(VALID_COURSE_NAME, VALID_COURSE_HOURS);
//        /* Add 5 students, enroll only 3 of them to see if the
//            method takes only the enrolled ones into consideration */
//        Set<Student> testStudents = generateValidStudents(5);
//        testStudents.stream()
//                .limit(3)
//                .forEach(testCourse::enrollStudent);
//        return new Object[][]{
//                {testCourse, testStudents}
//        };
//    }
//
//    private Set<Student> generateValidStudents(int numOfStudents) {
//        Set<Student> testStudents = new TreeSet<>();
//        for (int i = 0; i < numOfStudents; i++) {
//            Student currentStudent = new Student(VALID_STUDENT_NAME + i, 20 + i);
//            testStudents.add(currentStudent);
//        }
//        return testStudents;
//    }
//
//    @Test(dataProvider = "unsortedCourseData")
//    @Parameters({"unsortedCourses", "sortedCoursesByName"})
//    public void sortCoursesByName_UnsortedCoursesGiven_ShouldWorkCorrectly
//            (Set<Course> unsortedCourses, Set<Course> sortedCoursesByName) {
//        // Arrange: Done by data provider
//
//        // Act:
//        Set<Course> actualCourses = this.courseService.sortCoursesByName(unsortedCourses);
//
//        // Assert:
//        Assert.assertEquals(actualCourses, sortedCoursesByName);
//    }
//
//    @DataProvider
//    public Object[][] unsortedCourseData() {
//        Set<Course> unsortedCourses = new TreeSet<>();
//        Set<Course> sortedCoursesByName;
//        for (int i = 0; i < 5; i++) {
//            unsortedCourses.add(new Course(
//                    VALID_COURSE_NAME + (Math.random() * 10),
//                    VALID_COURSE_HOURS));
//        }
//        sortedCoursesByName = unsortedCourses.stream()
//                .sorted(Comparator.comparing(Course::getName))
//                .collect(Collectors.toCollection(TreeSet::new));
//        return new Object[][]{
//                {unsortedCourses, sortedCoursesByName}
//        };
//    }
//
//    @Test()
//    @Parameters({"courseWithStudents"})
//    public void sortStudentsByCourseAverageGrade_ValidCourseWithStudentsGiven_ShouldWorkCorrectly
//            (Course courseWithStudents) throws CourseNotFoundException, StudentNotFoundException {
//        CourseService spyService = Mockito.spy(this.courseService);
//        for (int i = 2; i <= 6; i++) {
//            doReturn(i)
//                    .when(spyService.getAverageGradeForStudentInCourse(anyLong(), anyLong()));
//        }
//        Assert.assertEquals();
//
//    }
//
//    @DataProvider
//    public Object[][] validCourseWithStudentsData() {
//        Course validCourse = new Course(VALID_COURSE_NAME, VALID_COURSE_HOURS);
//
//        final int numOfGradesPerStudent = 3;
//        final int numOfStudents = 10;
//        Set<Student> validStudents = this.generateValidStudents(numOfStudents);
//        Set<Student> studentsSortedByAverageGrade
//        // Enroll each student in validCourse and add numOfGradesPerStudent random grades to him.
//        validStudents
//                .forEach(validCourse::enrollStudent);
//        for (Student student : validStudents) {
//            for (int i = 0; i < numOfGradesPerStudent; i++) {
//                validCourse.addStudentGrade(student, this.generateRandomValidGrade());
//            }
//        }
//        return new Object[][]{
//                {validCourse}
//        };
//    }
//
//    private double generateRandomValidGrade() {
//        final int lowerBound = 2;
//        final int upperBound = 6;
//        return (Math.random() * upperBound) + lowerBound;
//    }
//
//    @Test(expectedExceptions = EntityNotFoundException.class)
//    public void find_IdOfNotAddedCourseGiven_ShouldThrowException()
//            throws EntityNotFoundException {
//        // Arrange:
//        when(this.mockCourseRepo.findById(anyLong()))
//                .thenReturn(null);
//        // Act:
//        // Annotation contains expected exceptions
//        this.courseService.findById(VALID_ID);
//    }
//
//    @Test(expectedExceptions = EmptyStorageException.class)
//    public void findAll_NoCoursesAdded_ShouldThrowException()
//            throws EmptyStorageException {
//        // Arrange:
//        // Act:
//        this.courseService.findAll();
//        // Assert:
//        // Annotation contains expected exceptions
//    }
}