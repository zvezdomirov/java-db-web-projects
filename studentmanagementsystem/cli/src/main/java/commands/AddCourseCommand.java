package commands;

import Services.CourseService;
import enums.CommandRegexPatterns;

import java.util.regex.Pattern;

public class AddCourseCommand implements Command {
    private CourseService courseService;
    private static AddCourseCommand instance;

    private AddCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    public static AddCourseCommand from(CourseService courseService) {
        if (null == instance ||
                instance.courseService != courseService) {
            instance = new AddCourseCommand(courseService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        if (Pattern.matches(
                CommandRegexPatterns.ADD_COURSE.get(), commandLine)) {
            String[] tokens = commandLine.split("\\s+");
            String courseName = tokens[1];
            int courseHours = Integer.parseInt(tokens[2]);
            this.courseService.addCourse(courseName, courseHours);
            // Message printed from repository
            return true;
        }
        return false;
    }
}
