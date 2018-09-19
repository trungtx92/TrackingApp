package t.mad.trackingapp.Model;

import java.io.Serializable;

public class RouteInfo implements Serializable {
    private String date;
    private String trackableID;
    private String stopTime;
    private String X;
    private String Y;
    private String stationary;


    public RouteInfo(String date, String trackableID, String stopTime, String x, String y, String stationary) {
        this.date = date;
        this.trackableID = trackableID;
        this.stopTime = stopTime;
        this.X = x;
        this.Y = y;
        this.stationary = stationary;
    }

    public String getDate() {
        return date;
    }

    public String getTrackableID() {
        return trackableID;
    }

    public String getStopTime() {
        return stopTime;
    }

    public String getX() {
        return X;
    }

    public String getY() {
        return Y;
    }

    public String getStationary() {
        return stationary;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTrackableID(String trackableID) {
        this.trackableID = trackableID;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public void setX(String x) {
        X = x;
    }

    public void setY(String y) {
        Y = y;
    }

    public void setStationary(String stationary) {
        this.stationary = stationary;
    }


}
