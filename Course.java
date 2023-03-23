package tracker;

public class Course {
    private final String name;
    private int points;

    public Course(String name) {
        this.name = name;
        this.points = 0;
    }

    public void addPoints(int points) {
        this.points += points;
    }

    @Override
    public String toString() {
        return name.concat("=").concat(String.valueOf(points));
    }
}
