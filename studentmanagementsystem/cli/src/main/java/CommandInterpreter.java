import Services.CourseService;
import Services.StudentService;
import Services.TeacherService;
import commands.*;

public class CommandInterpreter {
    private StudentService studentService;
    private TeacherService teacherService;
    private CourseService courseService;

    public CommandInterpreter(StudentService studentService,
                              TeacherService teacherService,
                              CourseService courseService) {
        this.studentService = studentService;
        this.teacherService = teacherService;
        this.courseService = courseService;
    }

    public boolean interpretCommand(String commandLine) {
        String[] tokens = commandLine.split("\\s+");
        try {
            int commandNumber = Integer.parseInt(tokens[0]);
            switch (commandNumber) {
                case 1:
                    return AddCourseCommand
                            .from(this.courseService).execute(commandLine);
                case 2:
                    return AddStudentCommand
                            .from(this.studentService).execute(commandLine);
                case 3:
                    return AddTeacherCommand
                            .from(this.teacherService).execute(commandLine);
                case 4:
                    return AddTeacherToCourseCommand
                            .from(this.teacherService).execute(commandLine);
                case 5:
                    return AddStudentToCourseCommand
                            .from(this.studentService).execute(commandLine);
                case 6:
                    return AddGradeToStudentInCourseCommand
                            .from(this.courseService).execute(commandLine);
                case 7:
                    return ShowAllStudentsGroupedByCourseAndAverageGradeCommand
                            .from(this.courseService).execute(commandLine);
                case 8:
                    return ShowAllCoursesWithTeacherAndStudentsCommand
                            .from(this.courseService).execute(commandLine);
                case 9:
                    return ShowAverageGradeForStudentsInCourseCommand
                            .from(this.courseService).execute(commandLine);
                case 10:
                    return ShowStudentTotalAverageGradeCommand
                            .from(this.studentService).execute(commandLine);
                case 11:
                    return ShowCommandsMenuCommand
                            .from().execute(commandLine);
                default:
                    return false;
            }
        } catch (NumberFormatException nfe) {
            System.out.println("Command must begin with a NUMBER! (in the range of 1-12)");
        }
        return true;
    }
}
