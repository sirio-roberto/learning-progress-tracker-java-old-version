package tracker;

public enum CoursesEnum {
    JAVA("Java"),
    DSA("DSA"),
    DATABASES("Databases"),
    SPRING("Spring");

    private String name;

    CoursesEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    static public String[] getAllCourses() {
        String[] names = new String[CoursesEnum.values().length];
        for (int i = 0; i < CoursesEnum.values().length; i++) {
            names[i] = CoursesEnum.values()[i].name;
        }
        return names;
    }
}
