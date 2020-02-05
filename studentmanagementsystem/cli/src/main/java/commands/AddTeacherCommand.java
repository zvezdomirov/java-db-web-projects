package commands;

import Services.TeacherService;
import enums.CommandRegexPatterns;

import java.util.regex.Pattern;

public class AddTeacherCommand implements Command {
    private TeacherService teacherService;
    private static AddTeacherCommand instance;

    private AddTeacherCommand(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public static Command from(TeacherService teacherService) {
        if (null == instance ||
                instance.teacherService != teacherService) {
            instance = new AddTeacherCommand(teacherService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        if (Pattern.matches(CommandRegexPatterns.ADD_TEACHER.get(), commandLine)) {
            String[] tokens = commandLine.split("\\s+");
            String teacherName = tokens[1];
            String teacherDegree = tokens[2];
            this.teacherService.addNewTeacher(teacherName, teacherDegree);
            // Message printed from repository
            return true;
        }
        return false;
    }
}
