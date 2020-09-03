package mods;

public enum Place {

    SQUARE("Square", 1), ARROW("Arrow", 2), RHOMBUS("Rhombus", 3),
    TRIANGLE("Triangle", 4), CIRCLE("Circle", 5), CROSS("Cross", 6);

    String name;
    int place;

    Place(String name, int place) {
        this.name = name;
        this.place = place;
    }

    @Override
    public String toString() {
        return name;
    }
}
