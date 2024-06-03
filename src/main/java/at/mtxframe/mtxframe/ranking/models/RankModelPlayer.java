package at.mtxframe.mtxframe.ranking.models;

import at.mtxframe.mtxframe.MtxFrame;
import at.mtxframe.mtxframe.colors.format.ColorFormat;

import java.util.ArrayList;

public class RankModelPlayer {
    public MtxFrame plugin = MtxFrame.getPlugin();
    ColorFormat colorFormat = new ColorFormat(plugin.getColorAPI());

    String rankName;
    String rankPrefix;

    //Color formatting Colors

    ArrayList<String> rankPermissions;

    public RankModelPlayer() {
        rankName = "Spieler";
        rankPrefix = "Spieler";


        rankPermissions = new ArrayList<>();

    }


}
