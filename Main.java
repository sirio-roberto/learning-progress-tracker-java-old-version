package tracker;

import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);

    static HashSet<Student> students = new LinkedHashSet<>();
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
                case "list" -> listStudents();
                case "add points" -> addPoints();
                case "back" -> System.out.println("Enter 'exit' to exit the program.");
                case "exit" -> System.out.println("Bye!");
                default -> validateNonExpectedInput(userInput);
            }
        } while (!"exit".equals(userInput));
    }

    private static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        String userInput = scan.nextLine();
        while (!"back".equals(userInput)) {
            if (!isValidPointInput(userInput)) {
                userInput = scan.nextLine();
            }
            String[] inputFields = userInput.split(" ");
            Optional<Student> student = students.stream().filter(s -> s.getId().equals(inputFields[0])).findAny();
            if (student.isPresent()) {
                int[] points = Arrays.stream(inputFields).skip(1).mapToInt(Integer::parseInt).toArray();
                student.get().addPointsToCourses(points);
                System.out.println("Points updated.");
            }
            // TODO this should never happen
            System.out.println("Error while updating points.");
        }
    }

    private static boolean isValidPointInput(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            System.out.println("Incorrect point input.");
            return false;
        }
        String[] inputFields = userInput.split(" ");
        String studentId = inputFields[0];
        if (students.stream().map(Student::getId).noneMatch(i -> i.equals(studentId))) {
            System.out.printf("No student is found for id=%s.%n", studentId);
            return false;
        }
        if (inputFields.length == 5) {
            int[] points = Arrays.stream(inputFields).skip(1).mapToInt(Integer::parseInt).toArray();
            if (Arrays.stream(points).allMatch(i -> i > 0)) {
                return true;
            }
        }
        System.out.println("Incorrect points format.");
        return false;
    }

    private static void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Students:");
            for (Student student: students) {
                System.out.println(student.getId());
            }
        }
    }

    private static void addStudents() {
        int addedStudents = 0;
        System.out.println("Enter student credentials or 'back' to return:");
        String credentialsOrBack;
        do {
            credentialsOrBack = scan.nextLine();
            if (!credentialsOrBack.equals("back")) {
                if (Student.isValidCredentials(credentialsOrBack)) {
                    Student newStudent = new Student(credentialsOrBack.split(" "));
                    if (students.stream().map(Student::getEmailAddress)
                            .anyMatch(email -> email.equals(newStudent.getEmailAddress()))) {
                        System.out.println("This email is already taken.");
                    } else {
                        students.add(newStudent);
                        addedStudents++;
                        System.out.println("The student has been added.");
                    }
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
