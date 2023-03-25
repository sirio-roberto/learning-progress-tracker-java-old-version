package tracker;

public class Course implements Comparable<Course> {
    private final String name;
    private int points;

    private int pointsToComplete;

    private Student enrolledStudent;

    public Course(String name, int pointsToComplete, Student enrolledStudent) {
        this.name = name;
        this.points = 0;
        this.pointsToComplete = pointsToComplete;
        this.enrolledStudent = enrolledStudent;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public Student getEnrolledStudent() {
        return enrolledStudent;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    public double getCompletionPercentage() {
        double percentage = points * 100.0 / pointsToComplete;
        return Math.round(percentage * 10.0) / 10.0;
    }

    @Override
    public String toString() {
        return name.concat("=").concat(String.valueOf(points));
    }

    @Override
    public int compareTo(Course other) {
        int result = Double.compare(other.getCompletionPercentage(), this.getCompletionPercentage());
        if (result == 0) {
            return this.enrolledStudent.getId().compareTo(other.getEnrolledStudent().getId());
        }
        return result;
    }
}
