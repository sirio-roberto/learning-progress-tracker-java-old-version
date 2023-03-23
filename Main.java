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
                case "find" -> findStudent();
                case "back" -> System.out.println("Enter 'exit' to exit the program.");
                case "exit" -> System.out.println("Bye!");
                default -> validateNonExpectedInput(userInput);
            }
        } while (!"exit".equals(userInput));
    }

    private static void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        String userInput = scan.nextLine();
        while (!"back".equals(userInput)) {
            Student student = getStudentById(userInput);
            if (student == null) {
                System.out.printf("No student is found for id=%s.%n", userInput);
            } else {
                StringBuilder score = new StringBuilder(userInput);
                score.append(" points:");
                for(Course course: student.getCourses()) {
                    score.append(" ").append(course).append(";");
                }
                score.deleteCharAt(score.length() - 1);
                System.out.println(score);
            }

            userInput = scan.nextLine();
        }
    }

    private static Student getStudentById(String userInput) {
        return students.stream().filter(s -> s.getId().equals(userInput)).findAny().orElse(null);
    }

    private static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return:");
        String userInput = scan.nextLine();
        while (!"back".equals(userInput)) {
            while (!isValidPointInput(userInput)) {
                userInput = scan.nextLine();
                if ("back".equals(userInput)) {
                    break;
                }
            }
            String[] inputFields = userInput.split(" ");
            Optional<Student> student = students.stream().filter(s -> s.getId().equals(inputFields[0])).findAny();
            if (student.isPresent()) {
                int[] points = Arrays.stream(inputFields).skip(1).mapToInt(Integer::parseInt).toArray();
                student.get().addPointsToCourses(points);
                System.out.println("Points updated.");
            }

            userInput = scan.nextLine();
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
            String[] strPoints = Arrays.stream(inputFields).skip(1).toArray(String[]::new);
            int[] points = new int[4];
            for (int i = 0; i < points.length; i++) {
                try {
                    points[i] = Integer.parseInt(strPoints[i]);
                    if (points[i] < 0) {
                        throw new NumberFormatException("Negative number is not allowed");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Incorrect points format.");
                    return false;
                }
            }
            return true;
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
