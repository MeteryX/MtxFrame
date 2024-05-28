package at.mtxframe.mtxframe.ranking;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.ColorAPI;
import at.mtxframe.mtxframe.colors.format.ColorFormat;

public enum Ranks {
    OWNER("Inhaber"),
    MODERATOR("Moderator"),
    SUPPORTER("Supporter"),
    ULTRA("Ultra"),
    PLAYER("Spieler");


    private String prefix;

    Ranks(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
    

}
