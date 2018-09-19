package t.mad.trackingapp.Model;

import java.io.Serializable;

public class Tracking implements Serializable {
    private int trackingID;
    private String trackableID;
    private String title;
    private String tgStartTime;
    private String tgEndTime;
    private String meetTime;
    private String currLocation;
    private String meetLocation;

    public Tracking(int trackingID, String trackableID, String title, String tgStartTime, String tgEndTime, String meetTime, String currLocation, String meetLocation) {
        this.trackingID = trackingID;
        this.trackableID = trackableID;
        this.title = title;
        this.tgStartTime = tgStartTime;
        this.tgEndTime = tgEndTime;
        this.meetTime = meetTime;
        this.currLocation = currLocation;
        this.meetLocation = meetLocation;

    }

    public Tracking(int trackingID, String title) {
        this.trackingID = trackingID;
        this.title = title;
    }

    public int getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(int trackingID) {
        this.trackingID = trackingID;
    }

    public String getTrackableID() {
        return trackableID;
    }

    public void setTrackableID(String trackableID) {
        this.trackableID = trackableID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTgStartTime() {
        return tgStartTime;
    }

    public void setTgStartTime(String tgStartTime) {
        this.tgStartTime = tgStartTime;
    }

    public String getTgEndTime() {
        return tgEndTime;
    }

    public void setTgEndTime(String tgEndTime) {
        this.tgEndTime = tgEndTime;
    }

    public String getMeetTime() {
        return meetTime;
    }

    public void setMeetTime(String meetTime) {
        this.meetTime = meetTime;
    }

    public String getCurrLocation() {
        return currLocation;
    }

    public void setCurrLocation(String currLocation) {
        this.currLocation = currLocation;
    }

    public String getMeetLocation() {
        return meetLocation;
    }

    public void setMeetLocation(String meetLocation) {
        this.meetLocation = meetLocation;
    }
}
