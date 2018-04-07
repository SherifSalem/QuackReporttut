package com.example.android.quakereport;

/**
 * Created by sherif on 16/10/17.
 */

public class Earthquackes {
    private double mMag;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;

    public Earthquackes(double mMag, String mLocation, long timeInMilliseconds, String url) {
        this.mMag = mMag;
        this.mLocation = mLocation;
        this.mTimeInMilliseconds= timeInMilliseconds;
        this.mUrl = url;
    }

    public double getmMag() {
        return mMag;
    }

    public String getmLocation() {
        return mLocation;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }
    public String getmUrl(){return  mUrl;}
}
