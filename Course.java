package tracker;

public class Course {
    private final String name;
    private int points;

    private int pointsToComplete;

    public Course(String name, int pointsToComplete) {
        this.name = name;
        this.points = 0;
        this.pointsToComplete = pointsToComplete;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public String toString() {
        return name.concat("=").concat(String.valueOf(points));
    }
}
