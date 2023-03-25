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

    static public String[] getAllCourses(boolean caseSensitive) {
        String[] names = new String[CoursesEnum.values().length];
        for (int i = 0; i < CoursesEnum.values().length; i++) {
            if (caseSensitive) {
                names[i] = CoursesEnum.values()[i].name;
            } else {
                names[i] = CoursesEnum.values()[i].name.toLowerCase();
            }
        }
        return names;
    }

    public static String getCaseSensitiveName(String insensitiveName) {
        String[] names = getAllCourses(true);
        for (String name: names) {
            if (name.toLowerCase().equals(insensitiveName.toLowerCase())) {
                return name;
            }
        }
        return null;
    }
}
