package commands;

import Services.CourseService;
import enums.CommandRegexPatterns;
import exceptions.InvalidGradeException;
import exceptions.NoSuchCourseExistsException;
import exceptions.NoSuchStudentExistsException;

import java.util.regex.Pattern;

public class AddGradeToStudentInCourseCommand implements Command {
    private CourseService courseService;
    private static AddGradeToStudentInCourseCommand instance;

    private AddGradeToStudentInCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    public static AddGradeToStudentInCourseCommand from(CourseService courseService) {
        if (null == instance ||
                instance.courseService != courseService) {
            instance = new AddGradeToStudentInCourseCommand(courseService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        if (Pattern.matches(
                CommandRegexPatterns.ADD_GRADE_TO_STUDENT_IN_COURSE.get(), commandLine)) {
            String[] tokens = commandLine.split("\\s+");
            int studentId = Integer.parseInt(tokens[1]);
            String courseName = tokens[2];
            double grade = Double.parseDouble(tokens[3]);
            try {
                this.courseService.addGradeToStudent(studentId, courseName, grade);
                System.out.printf(
                        "Successfully added grade %.2f to student with id %d in course \"%s\"%n",
                        grade, studentId, courseName);
                return true;
            } catch (Exception e) {
                // Message is printed from exception
                return true; // Because exception is handled
            }
        }
        return false;
    }
}
