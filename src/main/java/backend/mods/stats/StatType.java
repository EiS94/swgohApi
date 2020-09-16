package backend.mods.stats;

public enum  StatType {

    DEFENSE("Abwehr"), DEFENSEPROD("% Abwehr"), OFFENSE("Angriff"), OFFENSEPROD("% Angriff"),
    EFFECT("Effektivität"), HEALTH("Gesundheit"), HEALTHPROD("% Gesundheit"),
    CRITDAM("kritischer Schaden"), CRITHID(" kritisches Ausweichen"),
    CRIDHITCHAN("% kritische-Treffer-Chance"), PROTECTION("Schutz"), PROTECTIONPROD("% Schutz"),
    SPEED("Tempo"), TENACITY("Zähigkeit");

    String name;

    StatType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
