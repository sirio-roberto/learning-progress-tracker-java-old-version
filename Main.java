package tracker;

import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);
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
                case "exit" -> System.out.println("Bye!");
                default -> validateNonExpectedInput(userInput);
            }
        } while (!"exit".equals(userInput));
    }

    private static void addStudents() {
    }

    private static void validateNonExpectedInput(String userInput) {
        if (userInput.matches("\\s*")) {
            System.out.println("No input.");
        } else {
            System.out.println("Error: unknown command!");
        }
    }
}
