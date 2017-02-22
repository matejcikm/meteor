package com.example.meteor.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * @author martin
 * @since 21/02/2017.
 */

public class Meteor extends RealmObject {

    @PrimaryKey
    @SerializedName("id")
    int id;

    @SerializedName("fall")
    String fall;

    @SerializedName("name")
    String name;

    @SerializedName("recclass")
    String recclass;

    @SerializedName("reclat")
    double recLat;

    @SerializedName("reclong")
    double recLong;

    @Expose
    Date yearInternal;

    @Expose
    double latitude;

    @Expose
    private double longitude;

    public int getId() {
        return id;
    }


    public String getFall() {
        return fall;
    }

    public String getName() {
        return name;
    }

    public String getRecclass() {
        return recclass;
    }

    public double getRecLat() {
        return recLat;
    }

    public double getRecLong() {
        return recLong;
    }

    public Date getYear() {
        return yearInternal;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setYear(Date year) {
        this.yearInternal = year;
    }
}
