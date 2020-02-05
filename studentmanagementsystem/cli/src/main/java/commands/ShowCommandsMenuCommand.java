package commands;

public class ShowCommandsMenuCommand implements Command{
    private static ShowCommandsMenuCommand instance;

    private ShowCommandsMenuCommand() {

    }

    public static ShowCommandsMenuCommand from() {
        if (null == instance) {
            instance = new ShowCommandsMenuCommand();
        }
        return instance;
    }

    @Override
    public boolean execute(String commandLine) {
        System.out.println("Available commands:\n" +
                "1 <course name> <course length> - add a new course\n" +
                "2 <student name> <student age> - add a new student\n" +
                "3 <teacher name> BSc/MSc/PHD - add a new teacher with degree\n" +
                "4 <teacher id> <course name> - add a teacher to a specific course\n" +
                "5 <student id> <course name> - add a student to a specific course\n" +
                "6 <student id> <course name> <grade> - add a grade for student in a specific course (<grade> 2.0-6.0)\n" +
                "7 - show all students grouped by course (alphabetically) and then by their average grade for the course (ascending)\n" +
                "8 - show all courses and their teachers and students (without grades).\n" +
                "9 <course name> - show the average grade for all students in a specific course.\n" +
                "10 <student id> - show a total average grade for student (between all of his courses using the average grade).\n" +
                "11 - show commands menu\n" +
                "12 - exit the program");
        return true;
    }
}
