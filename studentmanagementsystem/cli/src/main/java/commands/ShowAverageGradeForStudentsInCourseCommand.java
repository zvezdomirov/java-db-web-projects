package commands;

import Services.CourseService;
import enums.CommandRegexPatterns;

import java.util.regex.Pattern;

public class ShowAverageGradeForStudentsInCourseCommand implements Command {
    private CourseService courseService;
    private static ShowAverageGradeForStudentsInCourseCommand instance;

    private ShowAverageGradeForStudentsInCourseCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    public static ShowAverageGradeForStudentsInCourseCommand from(CourseService courseService) {
        if (null == instance ||
                instance.courseService != courseService) {
            instance = new ShowAverageGradeForStudentsInCourseCommand(courseService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        if (Pattern.matches(
                CommandRegexPatterns.SHOW_AVERAGE_GRADE_FOR_STUDENTS_IN_COURSE.get(), commandLine)) {
            String[] tokens = commandLine.split("\\s+");
            String courseName = tokens[1];
            System.out.println(this.courseService.showAverageCourseGrade(courseName));
            return true;
        }
        return false;
    }
}
