package at.mtxframe.mtxframe.penalites.utils;

public enum ReportReason {

    CHEATING("Cheating/Hacking"),
    SWEARING("Beleidigung/schimpfen"),
    PROVOCATION("Provokation"),
    MOBBING("Mobbing"),
    OTHER("Anderer Grund");

    private String prefix;

    ReportReason(String prefix) {
        this.prefix = name().toLowerCase();
    }

    public String getPrefix() {
        return prefix;
    }

}
