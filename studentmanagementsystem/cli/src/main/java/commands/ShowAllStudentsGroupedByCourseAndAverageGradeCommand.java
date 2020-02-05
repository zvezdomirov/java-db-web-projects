package commands;

import Services.CourseService;

public class ShowAllStudentsGroupedByCourseAndAverageGradeCommand implements Command {
    private CourseService courseService;
    private static ShowAllStudentsGroupedByCourseAndAverageGradeCommand instance;

    private ShowAllStudentsGroupedByCourseAndAverageGradeCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    public static ShowAllStudentsGroupedByCourseAndAverageGradeCommand from(CourseService courseService) {
        if (null == instance ||
                instance.courseService != courseService) {
            instance = new ShowAllStudentsGroupedByCourseAndAverageGradeCommand(courseService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        this.courseService.showAllStudentsGroupedByCourseAndGrade();
        return true;
    }
}
