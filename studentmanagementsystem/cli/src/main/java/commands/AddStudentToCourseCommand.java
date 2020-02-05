package commands;

import Services.StudentService;
import enums.CommandRegexPatterns;

import java.util.regex.Pattern;

public class AddStudentToCourseCommand implements Command {
    private StudentService studentService;
    private static AddStudentToCourseCommand instance;

    private AddStudentToCourseCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    public static AddStudentToCourseCommand from(StudentService studentService) {
        if (null == instance ||
                instance.studentService != studentService) {
            instance = new AddStudentToCourseCommand(studentService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        if (Pattern.matches(
                CommandRegexPatterns.ADD_STUDENT_TO_COURSE.get(), commandLine)) {
            String[] tokens = commandLine.split("\\s+");
            int studentId = Integer.parseInt(tokens[1]);
            String courseName = tokens[2];
            try {
                this.studentService.addStudentToCourse(courseName, studentId);
                System.out.printf(
                        "Successfully added student with id %d to course \"%s\"%n",
                        studentId,
                        courseName);
                return true;
            } catch (Exception e) {
                // Message is printed in the exception
                return false;
            }
        }
        return false;
    }
}
