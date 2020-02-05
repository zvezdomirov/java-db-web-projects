package commands;

import Services.TeacherService;
import enums.CommandRegexPatterns;

import java.util.regex.Pattern;

public class AddTeacherToCourseCommand implements Command {
    private TeacherService teacherService;
    private static AddTeacherToCourseCommand instance;

    private AddTeacherToCourseCommand(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    public static AddTeacherToCourseCommand from(TeacherService teacherService) {
        if (null == instance ||
                instance.teacherService != teacherService) {
            instance = new AddTeacherToCourseCommand(teacherService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        if (Pattern.matches(
                CommandRegexPatterns.ADD_TEACHER_TO_COURSE.get(), commandLine)) {
            String[] tokens = commandLine.split("\\s+");
            int teacherId = Integer.parseInt(tokens[1]);
            String courseName = tokens[2];
            this.teacherService.addTeacherToCourse(teacherId, courseName);
            System.out.printf(
                    "Successfully added teacher to course \"%s\".%n",
                    courseName);
            return true;
        }
        return false;
    }
}
