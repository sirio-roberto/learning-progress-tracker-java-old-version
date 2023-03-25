package tracker;

import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static Scanner scan = new Scanner(System.in);

    static HashSet<Student> students = new LinkedHashSet<>();

    static Map<String, Integer> popularityMap = new HashMap<>();

    static Map<String, Integer> activityMap = new HashMap<>();

    static Map<String, Double> difficultyMap = new HashMap<>();
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
                case "statistics" -> showStatistics();
                case "back" -> System.out.println("Enter 'exit' to exit the program.");
                case "exit" -> System.out.println("Bye!");
                default -> validateNonExpectedInput(userInput);
            }
        } while (!"exit".equals(userInput));
    }

    private static void showStatistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        String mostPopular = getMostPopular();
        System.out.printf("Most popular: %s%n", mostPopular);
        System.out.printf("Least popular: %s%n", getLeastPopular(mostPopular));

        System.out.printf("Highest activity: %s%n", getHighestActivity());
        System.out.printf("Lowest activity: %s%n", getLowestActivity());

        System.out.printf("Easiest activity: %s%n", getEasiestCourse());
        System.out.printf("Hardest activity: %s%n", getHardestCourse());
    }

    private static String getHardestCourse() {
        if (difficultyMap.isEmpty()) {
            return "n/a";
        }
        double minAvg = difficultyMap.values().stream()
                .filter(v -> v != 0.0).min(Double::compareTo).orElse(0.0);
        if (minAvg == 0.0) {
            return "n/a";
        }
        return difficultyMap.keySet().stream()
                .filter(k -> difficultyMap.get(k) == minAvg)
                .collect(Collectors.joining(", "));
    }

    private static String getEasiestCourse() {
        fillDifficultyMap();
        if (difficultyMap.isEmpty()) {
            return "n/a";
        }
        double maxAvg = difficultyMap.values().stream().max(Double::compareTo).orElse(0.0);
        if (maxAvg == 0.0) {
            return "n/a";
        }
        return difficultyMap.keySet().stream()
                .filter(k -> difficultyMap.get(k) == maxAvg)
                .collect(Collectors.joining(", "));
    }

    private static void fillDifficultyMap() {
        if (!activityMap.isEmpty()) {
            for (String k: activityMap.keySet()) {
                difficultyMap.put(k, 0.0);
            }
            for (Student student: students) {
                for (Course course: student.getCourses()) {
                    String key = course.getName();
                    difficultyMap.put(key, difficultyMap.get(key) + course.getPoints());
                }
            }
            for (String k: activityMap.keySet()) {
                double avgValue = 0.0;
                if (activityMap.get(k) > 0) {
                    avgValue = difficultyMap.get(k) / activityMap.get(k);
                }
                difficultyMap.put(k, avgValue);
            }
        }
    }

    private static String getLowestActivity() {
        if (activityMap.isEmpty() ||
                (activityMap.values().stream().distinct().count() == 1
                && activityMap.values().stream().noneMatch(i -> i == 0))) {
            return "n/a";
        }
        int minActivity = activityMap.values().stream().min(Integer::compareTo).orElse(0);
        return activityMap.keySet().stream()
                .filter(k -> activityMap.get(k) == minActivity)
                .collect(Collectors.joining(", "));
    }

    private static String getHighestActivity() {
        if (activityMap.isEmpty() || activityMap.values().stream().allMatch(i -> i == 0)) {
            return "n/a";
        }
        int maxActivity = activityMap.values().stream().max(Integer::compareTo).orElse(0);
        return activityMap.keySet().stream()
                .filter(k -> activityMap.get(k) == maxActivity)
                .collect(Collectors.joining(", "));
    }

    private static String getLeastPopular(String mostPopular) {
        if (mostPopular.equals("n/a") || mostPopular.split(" ").length == popularityMap.size()) {
            return  "n/a";
        }
        int min = popularityMap.values().stream().min(Integer::compareTo).orElse(0);
        return popularityMap.keySet().stream()
                .filter(k -> popularityMap.get(k) == min)
                .collect(Collectors.joining(", "));
    }

    private static String getMostPopular() {
        popularityMap = getCurrentPopularity();
        int max = popularityMap.values().stream().max(Integer::compareTo).orElse(0);
        if (max == 0) {
            return "n/a";
        }
        return popularityMap.keySet().stream()
                .filter(k -> popularityMap.get(k) == max)
                .collect(Collectors.joining(", "));
    }

    private static Map<String, Integer> getCurrentPopularity() {
        Map<String, Integer> map = new HashMap<>();
        for(Student student: students) {
            for (Course course: student.getCourses()) {
                if(!map.containsKey(course.getName())) {
                    map.put(course.getName(), course.getPoints() > 0 ? 1 : 0);
                } else {
                    if (course.getPoints() > 0) {
                        map.put(course.getName(), map.get(course.getName()) + 1);
                    }
                }
            }
        }
        return map;
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
                if (activityMap.isEmpty()) {
                    startActivityTracking(student.get());
                }
                registerActivity(points);
                System.out.println("Points updated.");
            }

            userInput = scan.nextLine();
        }
    }

    private static void registerActivity(int[] points) {
        int i = 0;
        for (String key: activityMap.keySet()) {
            if (points[i] > 0) {
                activityMap.put(key, activityMap.get(key) + 1);
            }
            i++;
        }
    }

    private static void startActivityTracking(Student student) {
        Arrays.stream(student.getCourses())
                .map(Course::getName)
                .forEach(course -> activityMap.put(course, 0));
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
