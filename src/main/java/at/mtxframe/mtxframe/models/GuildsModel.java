package at.mtxframe.mtxframe.models;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class GuildsModel {
    private String guildID;
    private String guildName;
    private String guildOwnerName;
    private int guildLevel = 1;
    private int guildMemberCount;
    private int guildMaxMembers = 10;
    private Date guildCreationDate;
    ArrayList<String> memberRanks = new ArrayList<>(Arrays.asList("GILDENLEITER","MITGLIED","KRIEGER","KOMMANDANT"));


    public GuildsModel(String guildID, String guildName, String guildOwnerName, int guildLevel, int guildMemberCount, int guildMaxMembers, Date guildCreationDate) {

        this.guildID = guildID;
        this.guildName = guildName;
        this.guildOwnerName = guildOwnerName;
        this.guildLevel = guildLevel;
        this.guildMemberCount = guildMemberCount;
        this.guildMaxMembers = guildMaxMembers;
        this.guildCreationDate = new Date();

    }
    public void levelUp(){
        this.guildLevel++;
    }
    public void incrementMembers(){
        this.guildMemberCount++;
    }
    public void decrementMembers(){
        this.guildMemberCount--;
    }

    public String getGuildID() {
        return guildID;
    }

    public void setGuildID(String guildID) {
        this.guildID = guildID;
    }

    public String getGuildName() {
        return guildName;
    }

    public void setGuildName(String guildName) {
        this.guildName = guildName;
    }

    public String getGuildOwnerName() {
        return guildOwnerName;
    }

    public void setGuildOwnerName(String guildOwnerName) {
        this.guildOwnerName = guildOwnerName;
    }

    public int getGuildLevel() {
        return guildLevel;
    }

    public void setGuildLevel(int guildLevel) {
        this.guildLevel = guildLevel;
    }

    public int getGuildMemberCount() {
        return guildMemberCount;
    }

    public void setGuildMemberCount(int guildMemberCount) {
        this.guildMemberCount = guildMemberCount;
    }

    public int getGuildMaxMembers() {
        return guildMaxMembers;
    }

    public void setGuildMaxMembers(int guildMaxMembers) {
        this.guildMaxMembers = guildMaxMembers;
    }

    public Date getGuildCreationDate() {
        return guildCreationDate;
    }

    public void setGuildCreationDate(Date guildCreationDate) {
        this.guildCreationDate = guildCreationDate;
    }

    public ArrayList<String> getMemberRanks() {
        return memberRanks;
    }
}