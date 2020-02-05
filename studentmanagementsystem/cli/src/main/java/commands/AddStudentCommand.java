package commands;

import Services.StudentService;
import enums.CommandRegexPatterns;

import java.util.regex.Pattern;

public class AddStudentCommand implements Command {
    private StudentService studentService;
    private static AddStudentCommand instance;

    public AddStudentCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    public static Command from(StudentService studentService) {
        if (null == instance ||
                instance.studentService != studentService) {
            instance = new AddStudentCommand(studentService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        if (Pattern.matches(
                CommandRegexPatterns.ADD_STUDENT.get(), commandLine)) {
            String[] tokens = commandLine.split("\\s+");
            String studentName = tokens[1];
            int studentAge = Integer.parseInt(tokens[2]);
            this.studentService.addStudent(studentName, studentAge);
            //Would print message here, but it's already in the student repository
            return true;
        }
        return false;
    }
}
