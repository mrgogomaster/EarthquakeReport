package com.example.android.earthquakereport;

/**
 * Created by Tapan on 11/17/2016.
 */

public class Earthquake {

    private double mMagnitude;
    private String mLocation;
    private long mtimeInMilliSec;
    private String mUrl;

    public Earthquake(double magnitude,String location,long timeInMilliSec,String url){
        mMagnitude=magnitude;
        mLocation=location;
        mtimeInMilliSec=timeInMilliSec;
        mUrl=url;
    }

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public long getTimeInMilliSec() {return mtimeInMilliSec;}

    public String getUrl() {
        return mUrl;
    }
}
