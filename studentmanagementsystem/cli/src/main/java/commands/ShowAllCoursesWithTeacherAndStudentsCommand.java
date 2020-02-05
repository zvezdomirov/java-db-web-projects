package commands;

import Services.CourseService;

public class ShowAllCoursesWithTeacherAndStudentsCommand implements Command {
    private CourseService courseService;
    private static ShowAllCoursesWithTeacherAndStudentsCommand instance;

    private ShowAllCoursesWithTeacherAndStudentsCommand(CourseService courseService) {
        this.courseService = courseService;
    }

    public static ShowAllCoursesWithTeacherAndStudentsCommand from(CourseService courseService) {
        if (null == instance ||
                instance.courseService != courseService) {
            instance = new ShowAllCoursesWithTeacherAndStudentsCommand(courseService);
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        this.courseService.showAllCoursesAndAttendants();
        return true;
    }
}
