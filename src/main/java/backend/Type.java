package backend;

public enum Type {

    HEALTH("Health"), DEFENSE("Defense"), CRITDAM("critical Damage"), CRITCH("critical chance"),
    TENACITY("Tenacity"), OFFENSE("Offense"), EFFECT("Effectivity"), SPEED("Speed");

    String name;

    Type(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
