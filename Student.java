package tracker;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Student {
    private String firstName;
    private String lastName;
    private String emailAddress;

    public Student(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public Student(String[] credentials) {
        this.firstName = credentials[0];
        this.lastName = getLastNameFromCredentials(credentials);
        this.emailAddress = credentials[credentials.length - 1];
    }

    private String getLastNameFromCredentials(String[] credentials) {
        StringBuilder result = new StringBuilder();
        for (int i = 1; i < credentials.length - 1; i++) {
            result.append(credentials[i]).append(" ");
        }
        return result.toString().trim();
    }

    public static boolean isValidCredentials(String userInput) {
        if (userInput == null || userInput.isBlank()) {
            System.out.println("Incorrect credentials.");
            return false;
        }
        String[] fields = userInput.split(" ");
        if (fields.length < 3) {
            System.out.println("Incorrect credentials.");
            return false;
        }
        String firstName = fields[0];
        String[] lastName = Arrays.copyOfRange(fields, 1, fields.length - 1);
        String emailAddress = fields[fields.length - 1];
        return isValidFirstName(firstName) && isValidLastName(lastName) && isValidEmailAddress(emailAddress);
    }

    private static boolean isValidEmailAddress(String emailAddress) {
        Matcher validEmail = Pattern.compile("\\w+[.-]*\\w*@\\w+\\.[a-zA-Z-0-9]+").matcher(emailAddress);
        boolean isValid = validEmail.matches();
        if (!isValid) {
            System.out.println("Incorrect email.");
            return false;
        }
        return true;
    }

    private static boolean isValidLastName(String[] lastName) {
        if (lastName.length == 1 && lastName[0].length() < 2) {
            System.out.println("Incorrect last name.");
            return false;
        }
        for (String name: lastName) {
            boolean isValid = isValidName(name);
            if (!isValid) {
                System.out.println("Incorrect last name.");
                return false;
            }
        }
        return true;
    }

    private static boolean isValidFirstName(String firstName) {
        if (firstName.length() < 2) {
            System.out.println("Incorrect first name.");
            return false;
        }
        boolean isValid = isValidName(firstName);
        if (!isValid) {
            System.out.println("Incorrect first name.");
        }
        return isValid;
    }

    private static boolean isValidName(String name) {
        Matcher validCharacters = Pattern.compile("^[a-zA-Z]+[a-zA-Z'-]*[a-zA-Z]+$").matcher(name);
        Matcher doubleSymbols = Pattern.compile("['-]{2,}").matcher(name);
        return validCharacters.matches() && !doubleSymbols.find();
    }
}
