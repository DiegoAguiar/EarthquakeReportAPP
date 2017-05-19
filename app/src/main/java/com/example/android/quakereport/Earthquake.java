package com.example.android.quakereport;

import java.util.Date;

/**
 * Created by diego.almeida on 04/05/2017.
 */
public class Earthquake {
    private Double mMag;
    private String mLocation;
    private Date mEventDate;
    private int mMagColor;
    private String mUrl;


    public Earthquake(Double magnitude, String location, Date eventDate, int magColor, String url){
        this.mMag = magnitude;
        this.mLocation = location;
        this.mEventDate = eventDate;
        this.mMagColor = magColor;
        this.mUrl = url;
    }

    public Date getEventDate() {
        return mEventDate;
    }

    public void setEventDate(Date eventDate) {
        this.mEventDate = eventDate;
    }

    public Double getMagitude() {
        return mMag;
    }

    public void setMagitude(Double mMag) {
        this.mMag = mMag;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public int getIdMagColor() {
        return mMagColor;
    }

    public void setIdMagColor(int mMagColor) {
        this.mMagColor = mMagColor;
    }

    public String getUrl(){
        return mUrl;
    }

    public void setUrl(String url){
        this.mUrl = url;
    }
}
