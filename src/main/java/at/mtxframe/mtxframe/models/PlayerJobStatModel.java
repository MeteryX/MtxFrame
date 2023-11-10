package at.mtxframe.mtxframe.models;

import java.util.Date;

public class PlayerJobStatModel {
    String uuid;
    double balance;
    int miningLevel;
    double miningXP;
    int farmerLevel;
    double farmerXP;
    int hunterLevel;
    double hunterXP;
    int woodcutterLevel;
    double woodcutterXP;
    int fisherLevel;
    double fisherXP;

    public PlayerJobStatModel(String uuid, int miningLevel, double miningXP, int farmerLevel, double farmerXP, int hunterLevel, double hunterXP, int woodcutterLevel, double woodcutterXP, int fisherLevel, double fisherXP) {
        this.uuid = uuid;
        this.miningLevel = miningLevel;
        this.miningXP = miningXP;
        this.farmerLevel = farmerLevel;
        this.farmerXP = farmerXP;
        this.hunterLevel = hunterLevel;
        this.hunterXP = hunterXP;
        this.woodcutterLevel = woodcutterLevel;
        this.woodcutterXP = woodcutterXP;
        this.fisherLevel = fisherLevel;
        this.fisherXP = fisherXP;

    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMiningLevel() {
        return miningLevel;
    }

    public void setMiningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
    }

    public double getMiningXP() {
        return miningXP;
    }

    public void setMiningXP(double miningXP) {
        this.miningXP = miningXP;
    }

    public int getFarmerLevel() {
        return farmerLevel;
    }

    public void setFarmerLevel(int farmerLevel) {
        this.farmerLevel = farmerLevel;
    }

    public double getFarmerXP() {
        return farmerXP;
    }

    public void setFarmerXP(double farmerXP) {
        this.farmerXP = farmerXP;
    }

    public int getHunterLevel() {
        return hunterLevel;
    }

    public void setHunterLevel(int hunterLevel) {
        this.hunterLevel = hunterLevel;
    }

    public double getHunterXP() {
        return hunterXP;
    }

    public void setHunterXP(double hunterXP) {
        this.hunterXP = hunterXP;
    }

    public int getWoodcutterLevel() {
        return woodcutterLevel;
    }

    public void setWoodcutterLevel(int woodcutterLevel) {
        this.woodcutterLevel = woodcutterLevel;
    }

    public double getWoodcutterXP() {
        return woodcutterXP;
    }

    public void setWoodcutterXP(double woodcutterXP) {
        this.woodcutterXP = woodcutterXP;
    }

    public int getFisherLevel() {
        return fisherLevel;
    }

    public void setFisherLevel(int fisherLevel) {
        this.fisherLevel = fisherLevel;
    }

    public double getFisherXP() {
        return fisherXP;
    }

    public void setFisherXP(double fisherXP) {
        this.fisherXP = fisherXP;
    }
}
