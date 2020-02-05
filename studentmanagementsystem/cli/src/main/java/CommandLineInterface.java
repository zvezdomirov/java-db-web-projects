import Services.CourseService;
import Services.StudentService;
import Services.TeacherService;

import java.util.Scanner;

public class CommandLineInterface {
    private CommandInterpreter commandInterpreter;
    private Scanner scanner;

    public CommandLineInterface() {
        this.commandInterpreter = new CommandInterpreter(
                new StudentService(),
                new TeacherService(),
                new CourseService());
        this.scanner = new Scanner(System.in);
    }

    public void interpretConsoleCommands() {
        final String exitCommandNumber = "12";
        // Print greeting and commands menu
        this.printGreetingAndMenu();
        String currentLine;
        while (!(currentLine = this.scanner.nextLine().trim())
                .equals(exitCommandNumber)) {
            if (!this.commandInterpreter.interpretCommand(currentLine)) {
                System.out.println("Invalid Command! Try again!");
            }
            System.out.println("Enter command:");
        }
        System.out.println("Exiting the program...");
    }

    private void printGreetingAndMenu() {
        final String printMenuCommandNumber = "11";
        System.out.println("Welcome to THE Student Management application!");
        this.commandInterpreter.interpretCommand(printMenuCommandNumber);
    }
}
