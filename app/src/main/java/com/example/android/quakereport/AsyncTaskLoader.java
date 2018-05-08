package com.example.android.quakereport;

import android.content.Context;
import android.util.Log;

import java.util.List;

public class AsyncTaskLoader extends android.content.AsyncTaskLoader<List<Earthquackes>>{
    private static final String LOG_TAG = AsyncTaskLoader.class.getSimpleName();
    private String mUrl;

    public AsyncTaskLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.v(LOG_TAG,"onStartLoading");
    }

    @Override
    public List<Earthquackes> loadInBackground() {
        Log.v(LOG_TAG,"loading in backgroung");
        if (mUrl == null){
            return null;
        }
        List<Earthquackes> earthquackes = QueryUtils.fetchEarthquakeData(mUrl);
                return earthquackes;
    }
}
