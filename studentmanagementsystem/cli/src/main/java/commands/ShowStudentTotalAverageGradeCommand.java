package commands;

import Services.StudentService;
import enums.CommandRegexPatterns;

import java.util.regex.Pattern;

public class ShowStudentTotalAverageGradeCommand implements Command {
    private StudentService studentService;
    private static ShowStudentTotalAverageGradeCommand instance;

    private ShowStudentTotalAverageGradeCommand(StudentService studentService) {
        this.studentService = studentService;
    }

    public static ShowStudentTotalAverageGradeCommand from(StudentService studentService) {
        if (null == instance ||
                instance.studentService != studentService) {
            instance = new ShowStudentTotalAverageGradeCommand(studentService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        if (Pattern.matches(
                CommandRegexPatterns.SHOW_STUDENT_TOTAL_AVERAGE_GRADE.get(), commandLine)) {
            String[] tokens = commandLine.split("\\s+");
            int studentId = Integer.parseInt(tokens[1]);
            System.out.println(this.studentService.getGPA(studentId));
            return true;
        }
        return false;
    }
}
