package at.mtxframe.mtxframe.customitems;

public class ItemTrackingHandler {

    String woodTracker = "CI_WOOD_TRACKER";
    String oreTracker = "CI_ORE_TRACKER";
    String farmTracker = "CI_FARM_TRACKER";
    String fishTracker = "CI_FISH_TRACKER";

    String trackingType = "PLACEHOLDER";

    //Getter und Setter für Placeholder/trackingType

    public String getTrackingType() {
        return trackingType;
    }

    public void setTrackingType(String trackingType) {
        this.trackingType = trackingType;
    }


    //Getter und Setter für TrackStats
    public String getWoodTracker() {
        return woodTracker;
    }

    public String getOreTracker() {
        return oreTracker;
    }

    public String getFarmTracker() {
        return farmTracker;
    }

    public String getFishTracker() {
        return fishTracker;
    }
}
