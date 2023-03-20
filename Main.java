package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);

    static List<Student> students = new ArrayList<>();
    public static void main(String[] args) {
        startProgram();
    }

    private static void startProgram() {
        System.out.println("Learning Progress Tracker");
        showMainMenu();
    }

    private static void showMainMenu() {
        String userInput;
        do {
            userInput = scan.nextLine();
            switch (userInput) {
                case "add students" -> addStudents();
                case "back" -> System.out.println("Enter 'exit' to exit the program.");
                case "exit" -> System.out.println("Bye!");
                default -> validateNonExpectedInput(userInput);
            }
        } while (!"exit".equals(userInput));
    }

    private static void addStudents() {
        int addedStudents = 0;
        System.out.println("Enter student credentials or 'back' to return:");
        String credentialsOrBack;
        do {
            credentialsOrBack = scan.nextLine();
            if (!credentialsOrBack.equals("back")) {
                if (Student.isValidCredentials(credentialsOrBack)) {
                    students.add(new Student(credentialsOrBack.split(" ")));
                    addedStudents++;
                    System.out.println("The student has been added.");
                }
            }
        } while (!credentialsOrBack.equals("back"));
        System.out.printf("Total %d students have been added.%n", addedStudents);
    }

    private static void validateNonExpectedInput(String userInput) {
        if (userInput.matches("\\s*")) {
            System.out.println("No input.");
        } else {
            System.out.println("Error: unknown command!");
        }
    }
}
